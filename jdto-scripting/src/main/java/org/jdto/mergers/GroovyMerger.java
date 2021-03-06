/*
 *    Copyright 2012 Juan Alberto López Cavallotti
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jdto.mergers;

import groovy.lang.Binding;
import org.jdto.SinglePropertyValueMerger;

/**
 * Merge the source value by evaluating a Groovy Expression.<br />
 * 
 * This merger uses the embedded Groovy engine to provide convenience and 
 * simplicity to specify complex transformations. <br />
 * 
 * In order to access the input of this merger, a scoped variable called
 * <code>sourceValue</code> has been introduced.<br />
 * 
 * For convenience, the following packages have been imported: 
 * 
 * <ul>
 *  <li>org.apache.commons.lang.*</li>
 *  <li>java.util.*</li>
 *  <li>java.math.*</li>
 * </ul>
 * 
 * Finally, a scoped variable called <code>logger</code> has been introduced to help
 * with logging.
 * 
 * Here are some examples:<br />
 * 
 * <pre>
 * "sourceValue.toString()" //convert to string.
 * "sourceValue == null ? 'isNull' : 'isNotNull'" //check for nulls
 * "StringUtils.reverse(sourceValue)" //Access the StringUtils class from commons.
 * "logger.error('Value is: ' + sourceValue); return sourceValue;" //log the value and return it.
 * </pre>
 * 
 * @author Juan Alberto López Cavallotti
 */
public class GroovyMerger extends AbstractGroovyMerger implements SinglePropertyValueMerger<Object, Object> {
    private static final long serialVersionUID = 1L;
    
    private static final String SOURCE_VARIABLE_NAME = "sourceValue";
    
    /**
     * Merge the source value by applying the groovy expression sent as the first
     * merger parameter. <br />
     * @param value the object to be merged.
     * @param extraParam the first element is the groovy expression to evaluate.
     * @return the result of evaluating the expression.
     */
    @Override
    public Object mergeObjects(Object value, String[] extraParam) {
        
        String expression = getExpression(extraParam);
        
        //create a new groovy binding.
        Binding binding = new Binding();
        
        //set the source variable name.
        binding.setVariable(SOURCE_VARIABLE_NAME, value);
        
        return evaluateGroovyScript(binding, expression);
    }
}
