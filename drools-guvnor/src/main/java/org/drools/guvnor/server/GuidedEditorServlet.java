/**
 * Copyright 2010 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.drools.guvnor.server;


import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GuidedEditorServlet extends HttpServlet {

    public static enum GUIDED_EDITOR_SERVLET_PARAMETERS{
        GE_PACKAGE_PARAMETER_NAME("packageName",false),
        GE_CATEGORY_PARAMETER_NAME("categoryName",false),
        GE_BRL_PARAMETER_NAME("brlSource",true),
        
        GE_HIDE_RULE_LHS_PARAMETER_NAME("hideRuleLHS",false),
        GE_HIDE_RULE_RHS_PARAMETER_NAME("hideRuleRHS",false),
        GE_HIDE_RULE_ATTRIBUTES_PARAMETER_NAME("hideRuleAttributes",false);
        
        private String parameterName;
        private boolean multipleValues;

        private GUIDED_EDITOR_SERVLET_PARAMETERS(String parameterName, boolean multipleValues){
            this.parameterName = parameterName;
            this.multipleValues = multipleValues;
        }
        
        public String getParameterName() {
            return parameterName;
        }

        public boolean isMultipleValues() {
            return multipleValues;
        }
        
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        
        //copy each registered parameter from request to session
        for (GUIDED_EDITOR_SERVLET_PARAMETERS parameter : GUIDED_EDITOR_SERVLET_PARAMETERS.values()) {
            if (parameter.isMultipleValues()){
                session.setAttribute(parameter.getParameterName(), req.getParameterValues(parameter.getParameterName()));
            }else{
                session.setAttribute(parameter.getParameterName(), req.getParameter(parameter.getParameterName()));
            }
        }
        
        resp.sendRedirect("GuidedEditor.html?"+req.getQueryString());
    }

	

}