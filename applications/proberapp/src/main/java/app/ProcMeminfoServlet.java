/*
 * Copyright 2021 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package app;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.StringBuilder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Returns memory related info from jmx.
 */
@WebServlet(name = "ProcMeminfoServlet", value = "/proc_meminfo")
public class ProcMeminfoServlet extends HttpServlet {
  private static final Logger logger = Logger.getLogger(ProcMeminfoServlet.class.getName());

  private static String getMeminfo() throws IOException {
    StringBuilder result = new StringBuilder();
    String file = "/proc/meminfo";
    try (BufferedReader br = Files.newBufferedReader(Paths.get(file), UTF_8)) {
      String line;
      while ((line = br.readLine()) != null) {
        result.append(line).append("\n");
      }
    }
    return result.toString();
  }

  @Override
  public void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    logger.log(Level.INFO, "ProcMeminfoServlet - Executing servlet request for:" + req.getServletPath());
    resp.setCharacterEncoding("UTF-8");
    resp.setContentType("text/html");
    resp.getWriter().println("ProcMeminfoServlet");
    resp.getWriter().println(getMeminfo());
  }
}
