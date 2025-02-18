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

import static java.beans.Introspector.decapitalize;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.sql.SQLXML;

import jakarta.xml.bind.DataBindingException;
import jakarta.xml.bind.JAXB;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.annotation.XmlRootElement;
import javax.xml.namespace.QName;

import org.jooq.Converter;
import org.jooq.ConverterContext;
import org.jooq.XML;

/**
 * A binding that binds JAXB-annotated {@link Object} types to {@link SQLXML}
 * types from your database.
 * <p>
 * Subtypes may extend this type to provide actual bound type.
 *
 * @author Lukas Eder
 */
public class AbstractXMLasObjectBinding<T> extends AbstractXMLBinding<T> {


    private final Converter<XML, T> converter;

    protected AbstractXMLasObjectBinding(final Class<T> theType) {
        this.converter = new XMLasObjectConverter<>(theType);
    }

    @Override
    public final Converter<XML, T> converter() {
        return converter;
    }

    private static final class XMLasObjectConverter<T> extends AbstractContextConverter<XML, T> {

        XmlRootElement        root;
        transient JAXBContext ctx;

        private XMLasObjectConverter(Class<T> type) {
            super(XML.class, type);

            this.root = type.getAnnotation(XmlRootElement.class);
            this.ctx = initCtx();
        }

        private final JAXBContext initCtx() {
            try {
                return JAXBContext.newInstance(toType());
            }
            catch (JAXBException e) {
                throw new DataBindingException(e);
            }
        }

        @Override
        public T from(XML t, ConverterContext scope) {
            if (t == null)
                return null;

            return JAXB.unmarshal(new StringReader("" + t), toType());
        }

        @Override
        public XML to(T u, ConverterContext scope) {
            if (u == null)
                return null;

            try {
                StringWriter s = new StringWriter();

                Object o = u;
                if (root == null)
                    o = new JAXBElement<>(new QName(decapitalize(toType().getSimpleName())), toType(), u);

                Marshaller m = ctx.createMarshaller();
                m.setProperty(Marshaller.JAXB_FRAGMENT, true);
                m.marshal(o, s);
                return XML.xml(s.toString());
            }
            catch (JAXBException e) {
                throw new DataBindingException(e);
            }
        }

        private void writeObject(ObjectOutputStream oos) throws IOException {
            oos.defaultWriteObject();
        }

        private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
            ois.defaultReadObject();

            ctx = initCtx();
        }
    }
}
