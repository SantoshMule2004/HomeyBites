package com.homeybites.payloads;

import java.util.EnumSet;

public enum RevenueGroupBy {

    DAY(EnumSet.of(
            DateFilter.TODAY,
            DateFilter.YESTERDAY,
            DateFilter.THIS_WEEK,
            DateFilter.LAST_WEEK,
            DateFilter.LAST_7_DAYS,
            DateFilter.LAST_15_DAYS
    )),

    WEEK(EnumSet.of(
            DateFilter.LAST_30_DAYS,
            DateFilter.THIS_MONTH,
            DateFilter.LAST_MONTH,
            DateFilter.LAST_3_MONTHS
    )),

    MONTH(EnumSet.of(
            DateFilter.LAST_6_MONTHS,
            DateFilter.THIS_YEAR,
            DateFilter.LAST_YEAR
    )),

    YEAR(EnumSet.of(
            DateFilter.LAST_2_YEARS,
            DateFilter.LAST_5_YEARS,
            DateFilter.ALL_TIME
    ));

    private final EnumSet<DateFilter> supportedFilters;

    RevenueGroupBy(EnumSet<DateFilter> supportedFilters) {
        this.supportedFilters = supportedFilters;
    }

    public void validate(DateFilter filter) {

        if (!supportedFilters.contains(filter)) {
            throw new IllegalArgumentException(
                    String.format("%s is not supported with %s", filter, this)
            );
        }
    }
}
