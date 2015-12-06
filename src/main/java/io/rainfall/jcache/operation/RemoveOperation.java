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
import io.rainfall.statistics.StatisticsHolder;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.cache.Cache;

import static io.rainfall.jcache.statistics.JCacheResult.EXCEPTION;
import static io.rainfall.jcache.statistics.JCacheResult.MISS;
import static io.rainfall.jcache.statistics.JCacheResult.REMOVE;

/**
 * @author Aurelien Broszniowski
 */

public class RemoveOperation<K, V> extends Operation {

  @Override
  public void exec(final StatisticsHolder statisticsHolder, final Map<Class<? extends Configuration>,
      Configuration> configurations, final List<AssertionEvaluator> assertions) throws TestException {

    CacheConfig<K, V> cacheConfig = (CacheConfig<K, V>)configurations.get(CacheConfig.class);
    SequenceGenerator sequenceGenerator = cacheConfig.getSequenceGenerator();
    final long next = sequenceGenerator.next();
    List<Cache<K, V>> caches = cacheConfig.getCaches();
    final ObjectGenerator<K> keyGenerator = cacheConfig.getKeyGenerator();
    for (final Cache<K, V> cache : caches) {
      boolean removed;
      long start = getTimeInNs();
      try {
        removed = cache.remove(keyGenerator.generate(next));
        long end = getTimeInNs();
        if (removed) {
          statisticsHolder.record(cache.getName(), (end - start), REMOVE);
        } else {
          statisticsHolder.record(cache.getName(), (end - start), MISS);
        }
      } catch (Exception e) {
        long end = getTimeInNs();
        statisticsHolder.record(cache.getName(), (end - start), EXCEPTION);
      }
    }
  }

  @Override
  public List<String> getDescription() {
    return Arrays.asList("remove(key)");
  }
}
