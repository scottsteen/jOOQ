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

package org.jooq.meta.yugabytedb;

import static org.jooq.impl.DSL.inline;
import static org.jooq.impl.DSL.unquotedName;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.meta.postgres.PostgresDatabase;

/**
 * @author Lukas Eder
 */
public class YugabyteDBDatabase extends PostgresDatabase {

    boolean connectionInitialised;

    @Override
    protected DSLContext create0() {
        DSLContext ctx = DSL.using(getConnection(), SQLDialect.YUGABYTEDB);

        if (!connectionInitialised && getConnection() != null) {
            connectionInitialised = true;

            // Work around YugabyteDB's distributed catalog, which performs very poorly when using nested loops
            // https://github.com/yugabyte/yugabyte-db/issues/9938
            ctx.set(unquotedName("enable_nestloop"), inline(false)).execute();
        }

        return ctx;
    }
}
