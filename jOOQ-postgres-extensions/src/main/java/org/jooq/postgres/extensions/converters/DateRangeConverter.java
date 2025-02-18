/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Other licenses:
 * -----------------------------------------------------------------------------
 * Commercial licenses for this work are available. These replace the above
 * ASL 2.0 and offer limited warranties, support, maintenance, and commercial
 * database integrations.
 *
 * For more information, please visit: http://www.jooq.org/licenses
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */
package org.jooq.postgres.extensions.converters;

import static org.jooq.postgres.extensions.types.DateRange.dateRange;

import java.sql.Date;

import org.jooq.postgres.extensions.types.DateRange;

/**
 * A converter for {@link DateRange}.
 *
 * @author Lukas Eder
 */
public class DateRangeConverter extends AbstractRangeConverter<Date, DateRange> {

    private static final Date      EPOCH = Date.valueOf("1970-01-01");
    private static final DateRange EMPTY = dateRange(EPOCH, EPOCH);

    public DateRangeConverter() {
        super(DateRange.class);
    }

    @Override
    final DateRange construct(String lower, boolean lowerIncluding, String upper, boolean upperIncluding) {
        return dateRange(
            lower == null ? null : Date.valueOf(lower),
            lowerIncluding,
            upper == null ? null : Date.valueOf(upper),
            upperIncluding
        );
    }

    @Override
    final DateRange empty() {
        return EMPTY;
    }
}
