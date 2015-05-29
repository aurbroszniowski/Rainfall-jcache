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

package io.rainfall.jcache.operation;

import io.rainfall.ObjectGenerator;
import io.rainfall.jcache.statistics.JCacheResult;
import io.rainfall.statistics.OperationFunction;

import javax.cache.Cache;

import static io.rainfall.jcache.statistics.JCacheResult.EXCEPTION;
import static io.rainfall.jcache.statistics.JCacheResult.GET;
import static io.rainfall.jcache.statistics.JCacheResult.MISS;

/**
 * @author Aurelien Broszniowski
 */
public class GetOperationFunction<K, V> implements OperationFunction<JCacheResult> {

  private Cache<K, V> cache;
  private long next;
  private ObjectGenerator<K> keyGenerator;

  public GetOperationFunction(final Cache<K, V> cache, final long next, final ObjectGenerator<K> keyGenerator) {
    this.cache = cache;
    this.next = next;
    this.keyGenerator = keyGenerator;
  }

  @Override
  public JCacheResult apply() throws Exception {
    V value;
    try {
      value = cache.get(keyGenerator.generate(next));
    } catch (Exception e) {
      return EXCEPTION;
    }
    if (value == null) {
      return MISS;
    } else {
      return GET;
    }
  }
}
