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
package org.jooq.impl;

import static org.jooq.impl.DSL.*;
import static org.jooq.impl.Internal.*;
import static org.jooq.impl.Keywords.*;
import static org.jooq.impl.Names.*;
import static org.jooq.impl.SQLDataType.*;
import static org.jooq.impl.Tools.*;
import static org.jooq.impl.Tools.BooleanDataKey.*;
import static org.jooq.impl.Tools.ExtendedDataKey.*;
import static org.jooq.impl.Tools.SimpleDataKey.*;
import static org.jooq.SQLDialect.*;

import org.jooq.*;
import org.jooq.Function1;
import org.jooq.Record;
import org.jooq.conf.*;
import org.jooq.impl.*;
import org.jooq.impl.QOM.*;
import org.jooq.tools.*;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;


/**
 * The <code>XOR</code> statement.
 */
@SuppressWarnings({ "unused" })
final class Xor
extends
    AbstractCondition
implements
    QOM.Xor
{

    final Condition arg1;
    final Condition arg2;

    Xor(
        Condition arg1,
        Condition arg2
    ) {

        this.arg1 = arg1;
        this.arg2 = arg2;
    }

    // -------------------------------------------------------------------------
    // XXX: QueryPart API
    // -------------------------------------------------------------------------



    private static final Clause[]        CLAUSES           = { Clause.CONDITION, Clause.CONDITION_XOR };
    private static final Set<SQLDialect> NO_SUPPORT_NATIVE = SQLDialect.supportedBy(DERBY, FIREBIRD, H2, HSQLDB, IGNITE, POSTGRES, SQLITE, YUGABYTEDB);

    @Override
    final boolean parenthesised(Context<?> ctx) {
        return !NO_SUPPORT_NATIVE.contains(ctx.dialect());
    }

    @Override
    public final void accept(Context<?> ctx) {







        if (NO_SUPPORT_NATIVE.contains(ctx.dialect())) {
            ctx.visit(arg1.ne(arg2));
        }
        else {
            ctx.sqlIndentStart('(');
            Expression.<Condition, Xor>acceptAssociative(
                ctx,
                this,
                q -> new Expression.Expr<>(q.arg1, Operator.XOR.toKeyword(), q.arg2),
                Context::formatSeparator
            );
            ctx.sqlIndentEnd(')');
        }
    }

    @Override
    final boolean isNullable() {
        return ((AbstractCondition) arg1).isNullable() || ((AbstractCondition) arg2).isNullable();
    }

    @Override
    public final Clause[] clauses(Context<?> ctx) {
        return CLAUSES;
    }












    // -------------------------------------------------------------------------
    // XXX: Query Object Model
    // -------------------------------------------------------------------------

    @Override
    public final Condition $arg1() {
        return arg1;
    }

    @Override
    public final Condition $arg2() {
        return arg2;
    }

    @Override
    public final QOM.Xor $arg1(Condition newValue) {
        return new Xor(newValue, $arg2());
    }

    @Override
    public final QOM.Xor $arg2(Condition newValue) {
        return new Xor($arg1(), newValue);
    }

    @Override
    public final Function2<? super Condition, ? super Condition, ? extends Condition> $constructor() {
        return (a1, a2) -> DSL.xor(a1, a2);
    }

    // -------------------------------------------------------------------------
    // XXX: The Object API
    // -------------------------------------------------------------------------

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.Xor o) {
            return
                StringUtils.equals($arg1(), o.$arg1()) &&
                StringUtils.equals($arg2(), o.$arg2())
            ;
        }
        else
            return super.equals(that);
    }
}
