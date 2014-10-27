/*
 * Copyright 2014 Aurélien Broszniowski
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.rainfall.jcache.statistics;

import org.rainfall.statistics.Result;

/**
 * @author Aurelien Broszniowski
 */

public class JCacheResult extends Result {
  public static Result PUT = new Result("PUT");
  public static Result GET = new Result("GET");
  public static Result MISS = new Result("MISS");
  public static Result REMOVE = new Result("REMOVE");
  public static Result EXCEPTION = new Result("EXCEPTION");

  public JCacheResult(final String result) {
    super(result);
  }

  public static Result[] values() {
    return new Result[] { PUT, GET, MISS, REMOVE, EXCEPTION };
  }
}
