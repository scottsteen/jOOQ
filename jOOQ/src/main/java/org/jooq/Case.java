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
package org.jooq;

import org.jooq.impl.DSL;

import org.jetbrains.annotations.NotNull;


/**
 * The SQL case statement.
 * <p>
 * This construct can be used to create expressions of the type <pre><code>
 * CASE x WHEN 1 THEN 'one'
 *        WHEN 2 THEN 'two'
 *        ELSE        'three'
 * END
 * </code></pre> or of the type <pre><code>
 * CASE WHEN x &lt; 1  THEN 'one'
 *      WHEN x &gt;= 2 THEN 'two'
 *      ELSE            'three'
 * END
 * </code></pre> Instances of Case are created through the
 * {@link DSL#decode()} method
 *
 * @author Lukas Eder
 */
public interface Case {

    /**
     * This construct can be used to create expressions of the type <pre><code>
     * CASE value WHEN 1 THEN 'one'
     *            WHEN 2 THEN 'two'
     *            ELSE        'three'
     * END
     * </code></pre>
     *
     * @param <V> The generic value type parameter
     * @param value The value to do the case statement on
     * @return An intermediary step for case statement construction
     */
    @NotNull
    @Support
    <V> CaseValueStep<V> value(V value);

    /**
     * This construct can be used to create expressions of the type <pre><code>
     * CASE value WHEN 1 THEN 'one'
     *            WHEN 2 THEN 'two'
     *            ELSE        'three'
     * END
     * </code></pre>
     *
     * @param <V> The generic value type parameter
     * @param value The value to do the case statement on
     * @return An intermediary step for case statement construction
     */
    @NotNull
    @Support
    <V> CaseValueStep<V> value(Field<V> value);

    /**
     * This construct can be used to create expressions of the type <pre><code>
     * CASE WHEN x &lt; 1  THEN 'one'
     *      WHEN x &gt;= 2 THEN 'two'
     *      ELSE            'three'
     * END
     * </code></pre>
     *
     * @param <T> The generic field type parameter
     * @param condition A condition to check in the case statement
     * @param result The result if the condition holds true
     * @return An intermediary step for case statement construction
     */
    @NotNull
    @Support
    <T> CaseConditionStep<T> when(Condition condition, T result);

    /**
     * This construct can be used to create expressions of the type <pre><code>
     * CASE WHEN x &lt; 1  THEN 'one'
     *      WHEN x &gt;= 2 THEN 'two'
     *      ELSE            'three'
     * END
     * </code></pre>
     *
     * @param <T> The generic field type parameter
     * @param condition A condition to check in the case statement
     * @param result The result if the condition holds true
     * @return An intermediary step for case statement construction
     */
    @NotNull
    @Support
    <T> CaseConditionStep<T> when(Condition condition, Field<T> result);

    /**
     * This construct can be used to create expressions of the type <pre><code>
     * CASE WHEN x &lt; 1  THEN 'one'
     *      WHEN x &gt;= 2 THEN 'two'
     *      ELSE            'three'
     * END
     * </code></pre>
     *
     * @param <T> The generic field type parameter
     * @param condition A condition to check in the case statement
     * @param result The result if the condition holds true
     * @return An intermediary step for case statement construction
     */
    @NotNull
    @Support
    <T> CaseConditionStep<T> when(Condition condition, Select<? extends Record1<T>> result);

    /**
     * This construct can be used to create expressions of the type <pre><code>
     * CASE WHEN x &lt; 1  THEN 'one'
     *      WHEN x &gt;= 2 THEN 'two'
     *      ELSE            'three'
     * END
     * </code></pre>
     *
     * @param <T> The generic field type parameter
     * @param condition A condition to check in the case statement
     * @param result The result if the condition holds true
     * @return An intermediary step for case statement construction
     */
    @NotNull
    @Support
    <T> CaseConditionStep<T> when(Field<Boolean> condition, T result);

    /**
     * This construct can be used to create expressions of the type <pre><code>
     * CASE WHEN x &lt; 1  THEN 'one'
     *      WHEN x &gt;= 2 THEN 'two'
     *      ELSE            'three'
     * END
     * </code></pre>
     *
     * @param <T> The generic field type parameter
     * @param condition A condition to check in the case statement
     * @param result The result if the condition holds true
     * @return An intermediary step for case statement construction
     */
    @NotNull
    @Support
    <T> CaseConditionStep<T> when(Field<Boolean> condition, Field<T> result);

    /**
     * This construct can be used to create expressions of the type <pre><code>
     * CASE WHEN x &lt; 1  THEN 'one'
     *      WHEN x &gt;= 2 THEN 'two'
     *      ELSE            'three'
     * END
     * </code></pre>
     *
     * @param <T> The generic field type parameter
     * @param condition A condition to check in the case statement
     * @param result The result if the condition holds true
     * @return An intermediary step for case statement construction
     */
    @NotNull
    @Support
    <T> CaseConditionStep<T> when(Field<Boolean> condition, Select<? extends Record1<T>> result);
}
