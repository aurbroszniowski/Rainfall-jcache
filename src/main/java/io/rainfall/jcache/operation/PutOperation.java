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

import io.rainfall.AssertionEvaluator;
import io.rainfall.Configuration;
import io.rainfall.ObjectGenerator;
import io.rainfall.Operation;
import io.rainfall.SequenceGenerator;
import io.rainfall.TestException;
import io.rainfall.jcache.CacheConfig;
import io.rainfall.jcache.statistics.JCacheResult;
import io.rainfall.statistics.StatisticsHolder;
import io.rainfall.statistics.Task;

import java.util.List;
import java.util.Map;

import javax.cache.Cache;

import static io.rainfall.jcache.statistics.JCacheResult.EXCEPTION;
import static io.rainfall.jcache.statistics.JCacheResult.PUT;

/**
 * @author Aurelien Broszniowski
 */

public class PutOperation<K, V> extends Operation {

  private double weight = 1;

  @Override
  public void exec(final StatisticsHolder statisticsHolder, final Map<Class<? extends Configuration>,
      Configuration> configurations, final List<AssertionEvaluator> assertions) throws TestException {

    CacheConfig<K, V> cacheConfig = (CacheConfig<K, V>)configurations.get(CacheConfig.class);
    SequenceGenerator sequenceGenerator = cacheConfig.getSequenceGenerator();
    final long next = sequenceGenerator.next();
    if (cacheConfig.getRandomizer().nextFloat(next) <= this.weight) {
      List<Cache<K, V>> caches = cacheConfig.getCaches();
      final ObjectGenerator<K> keyGenerator = cacheConfig.getKeyGenerator();
      final ObjectGenerator<V> valueGenerator = cacheConfig.getValueGenerator();
      for (final Cache<K, V> cache : caches) {
        statisticsHolder
            .measure(cache.getName(), new Task() {

              @Override
              public JCacheResult definition() throws Exception {
                try {
                  cache.put(keyGenerator.generate(next), valueGenerator.generate(next));
                } catch (Exception e) {
                  return EXCEPTION;
                }
                return PUT;
              }
            });
      }
    }
  }

  public Operation withWeight(double weight) {
    if (weight < 0 || weight > 1) {
      throw new IllegalStateException("Operation weight should be between 0.01 and 1.00 (0 and 100%) and is " + weight);
    }
    this.weight = weight;
    return this;
  }
}
