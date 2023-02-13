package com.acq.collection.acqcollectionbook.homepage.bestseller;

import com.acq.collection.acqcollectionbook.common.validation.ChaeumValidation;
import com.acq.collection.acqcollectionbook.homepage.collection.CollectionBestDivision;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@RequiredArgsConstructor
@Slf4j
@Service
public class BestSellerServiceImpl extends EgovAbstractServiceImpl implements BestSellerService {

    private final ChaeumValidation chaeumValidation;

    Document document;
    Elements elements;

    @Override
    public Map<String, Object> getBestSellerInfo(Map<String, Object> params, int _siteIndex, int _page, int _categoryIndex, int _listIndex, int _rank,
                                                 int _tryIndex, WebDriver driver) {
        List<String> weekOfMonthList = !this.chaeumValidation.isNull(params) ? this.getWeekOfMonthList(params) : this.getWeekOfMonthList(getCurrentWeek());
        int siteIndex = _siteIndex;
        int page = _page;
        int categoryIndex = _categoryIndex;
        int listIndex = _listIndex;
        int rank = _rank;
        int tryIndex = _tryIndex;

        String [] apiNames = {"KYOBO_API"};
        try {
            for (String api : apiNames) {
                CollectionBestDivision bestDivision = CollectionBestDivision.of(api);
                // 카테고리 조회
                driver.get(bestDivision.getApiUrl() + bestDivision.getCategoryUrl());
                document = Jsoup.parse(driver.getPageSource());
                elements = document.select(bestDivision.getCategoryEl());
                for (Element element : elements) {
                    String category = element.attr(bestDivision.getCategoryAttr()).replaceAll(bestDivision.getCategoryRegExp(), "");
                    log.info(category);
                }
            }
        } catch (Exception e) {
            log.error("BEST SELLER ERROR INFO : >> " + e);
            return this.chaeumValidation.errorResult("BestSeller API Fail");
        }
        return this.chaeumValidation.successResult(null);
    }

    private Map<String, Object> getCurrentWeek() {
        Calendar calendar = Calendar.getInstance();
        Calendar getFirstDayOfMonth = Calendar.getInstance();

        Map<String ,Object> map = new HashMap<>();

        //일요일인지 확인
        if(calendar.get(Calendar.DAY_OF_WEEK) != 1) {
            calendar.add(Calendar.DAY_OF_MONTH, 8 - calendar.get(Calendar.DAY_OF_WEEK));
            getFirstDayOfMonth.add(Calendar.DAY_OF_MONTH, 8 - getFirstDayOfMonth.get(Calendar.DAY_OF_WEEK));
        }

        calendar.add(Calendar.DAY_OF_MONTH, -7);
        getFirstDayOfMonth.set(Calendar.DAY_OF_MONTH, 1);

        String originalMonth = Integer.toString(calendar.get(Calendar.MONTH) + 1);

        map.put("bs_year", Integer.toString(calendar.get(Calendar.WEEK_OF_MONTH)));
        map.put("bs_month", originalMonth.length() > 1 ? originalMonth : "0" + originalMonth);

        if(getFirstDayOfMonth.get(Calendar.DAY_OF_WEEK) == 1) {
            map.put("bs_week", Integer.toString(calendar.get(Calendar.WEEK_OF_MONTH)));
        } else {
            map.put("bs_week", Integer.toString(calendar.get(Calendar.WEEK_OF_MONTH) -1 ));
        }
        return map;
    }

    private List<String> getWeekOfMonthList(Map<String, Object> map) {
        List<String> weekOfMonthList = new ArrayList<>();
        Calendar startCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd", Locale.KOREA);
            Date startDate = sdf.parse(map.get("startDate").toString());
            Date endDate = sdf.parse(map.get("endDate").toString());

            startCalendar.setTime(startDate);
            endCalendar.setTime(endDate);



            int endYear = endCalendar.get(Calendar.YEAR);
            int endMonth = endCalendar.get(Calendar.MONTH) + 1;
            int endDay = endCalendar.get(Calendar.DATE);

            String endCalendarBestWeek = getCurrentWeekOfMonth(endYear,endMonth,endDay);

            int i = 0;

            while(true) {

                if(i != 0) {
                    startCalendar.add(Calendar.DAY_OF_MONTH, + 7);
                }

                int startYear = startCalendar.get(Calendar.YEAR);
                int startMonth = startCalendar.get(Calendar.MONTH) + 1;
                int startDay = startCalendar.get(Calendar.DATE);

                weekOfMonthList.add(getCurrentWeekOfMonth(startYear,startMonth,startDay));

                if(weekOfMonthList.get(i).equals(endCalendarBestWeek) || i >= 52) break;

                i++;
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return weekOfMonthList;
    }

    public String getCurrentWeekOfMonth(int _year, int _month, int _day)  {
        Calendar calendar;
        int month = _month - 1;
        int day = _day;
        // 입력받은 날짜데이터의 해당월의 1일을 수요일을 기준으로 나누는 함수
        // firstWeekNumber 값이 -1 이 나오는 경우에는 이전 달 정보를 가져와야한다.
        // firstWeekNumber 값이 0 인경우는 Calendar 의 기본 주차 구하는 공식 적용
        // firstWeekNumber 값이 1 인경우는 Calendar 의 기본 주차 구하는 공식에 1을 빼줌
        int firstWeekNumber = subWeekNumberIsFirstDayAfterWednesday(_year, month, day);

        // lastWeekNumber 값이 0 인경우는 Calendar 의 기본 주차 구하는 공식 적용
        int lastWeekNumber = addMonthIsLastDayBeforeWednesday(_year, month, day);

        // lastWeekNumber 값이 1보다 크다는건 해당 월의 마지막 주차의 요일이 월,화요일이란 것이다.
        // 따라서 다음주로 넘긴다.
        if (lastWeekNumber > 0) {
            // 해당 날짜인 day 를 1로 초기화 시킨다.
            // 해당 월은 아래쪽에 코드에서 다음달로 넘길것이다.
            day = 1;
            firstWeekNumber = 0;
        }

        if (firstWeekNumber < 0)  {
            //Calendar 를 이전 달 정보로 세팅
            calendar = Calendar.getInstance(Locale.KOREA);
            calendar.set(_year, month, day);
            calendar.add(Calendar.DATE, -1);

            return getCurrentWeekOfMonth(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH) + 1), calendar.get(Calendar.DATE));
        }

        //Calendar 초기화
        calendar = Calendar.getInstance(Locale.KOREA);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setMinimalDaysInFirstWeek(1);
        calendar.set(_year, month - (1 - lastWeekNumber), day);
        // lastWeekNumber 이 1이 나온경우 Calendar 를 다음달로 세팅한다.

        int dayOfWeekForFirstDayOfMonth = calendar.get(Calendar.WEEK_OF_MONTH) - firstWeekNumber;

        //dayOfWeekForFirstDayOfMonth 인경우 기존 날짜에서 1일을 뺀 날자를 getCurrentWeekOfMonth 함수로 재귀
        if(dayOfWeekForFirstDayOfMonth == 0) {
            calendar.set(_year, month - 1, day);
            calendar.add(Calendar.DATE, -1);
            return getCurrentWeekOfMonth(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH) + 1), calendar.get(Calendar.DATE));
        }

        return _year + "-" + (Integer.toString(calendar.get(Calendar.MONTH) + 1).length() > 1 ?
                    calendar.get(Calendar.MONTH) + 1 : "0" + (calendar.get(Calendar.MONTH) + 1)) + "-" + dayOfWeekForFirstDayOfMonth;

    }
    public static int subWeekNumberIsFirstDayAfterWednesday(int year, int month, int day)  {
        //Calendar 를 입력받은 날짜로 초기화하고 day = 1일로 초기화
        Calendar calendar = Calendar.getInstance(Locale.KOREA);
        calendar.set(year, month - 1, day);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        // 해당 주의 시작일을 월요일로 셋팅
        calendar.setFirstDayOfWeek(Calendar.MONDAY);

        // 1일로 초기화한 Calendar 의 요일
        int weekOfDay = calendar.get(Calendar.DAY_OF_WEEK);

        // 입력받은 날짜의 해당 월의 1일이 월,화,수 요일경우
        if ((weekOfDay >= Calendar.MONDAY) && (weekOfDay <= Calendar.WEDNESDAY)) {
            return 0;
        }
        // 입력받은 날짜가 1이고 입력받은 날짜의 해당 월의 1일이 월,화,수요일이 아닐 경우
        else if (day == 1 && weekOfDay > Calendar.WEDNESDAY) {
            return -1;
        } else {
            return 1;
        }
    }

    public static int addMonthIsLastDayBeforeWednesday(int year, int month, int day) {
        //Calendar 를 파라미터로 입력받은 날짜로 초기화
        Calendar calendar = Calendar.getInstance(Locale.KOREA);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(year, month - 1, day);

        // 자바 Calendar 의 해당 주의 주차 구하기 방식
        int currentWeekNumber = calendar.get(Calendar.WEEK_OF_MONTH);
        // 자바 Calendar 의 해당 주의 최고 주차 구하기 방식
        int maximumWeekNumber = calendar.getActualMaximum(Calendar.WEEK_OF_MONTH);

        if (currentWeekNumber == maximumWeekNumber) {
            calendar.set(year, month - 1, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            int maximumDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            //월의 마지막 주차에 요일이 월,화인 경우 주차를 다음 주차정보를 가져와야함
            if (maximumDayOfWeek < Calendar.WEDNESDAY && maximumDayOfWeek > Calendar.SUNDAY) {
                return 1;
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

}
