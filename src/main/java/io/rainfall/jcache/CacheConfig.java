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

package io.rainfall.jcache;

import io.rainfall.Configuration;
import io.rainfall.ObjectGenerator;
import io.rainfall.generator.IterationSequenceGenerator;
import io.rainfall.utils.ConcurrentPseudoRandom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.cache.Cache;

/**
 * @author Aurelien Broszniowski
 */

public class CacheConfig<K, V> extends Configuration {

  private List<Cache<K, V>> caches = new ArrayList<Cache<K, V>>();
  private ObjectGenerator<K> keyGenerator = null;
  private ObjectGenerator<V> valueGenerator = null;
  private IterationSequenceGenerator sequenceGenerator = null;
  private ConcurrentPseudoRandom randomizer = new ConcurrentPseudoRandom();

  public static <K, V> CacheConfig<K, V> cacheConfig() {
    return new CacheConfig<K, V>();
  }

  public CacheConfig<K, V> caches(final Cache<K, V>... caches) {
    Collections.addAll(this.caches, caches);
    return this;
  }

  public CacheConfig<K, V> using(final ObjectGenerator<K> keyGenerator, final ObjectGenerator<V> valueGenerator) {
    if (this.keyGenerator != null) {
      throw new IllegalStateException("KeyGenerator already chosen.");
    }
    this.keyGenerator = keyGenerator;

    if (this.valueGenerator != null) {
      throw new IllegalStateException("ValueGenerator already chosen.");
    }
    this.valueGenerator = valueGenerator;
    return this;
  }

  public CacheConfig<K, V> sequentially() {
    if (this.sequenceGenerator != null) {
      throw new IllegalStateException("SequenceGenerator already chosen.");
    }
    this.sequenceGenerator = new IterationSequenceGenerator();
    return this;
  }

  public List<Cache<K, V>> getCaches() {
    return caches;
  }

  public ObjectGenerator<K> getKeyGenerator() {
    return keyGenerator;
  }

  public ObjectGenerator<V> getValueGenerator() {
    return valueGenerator;
  }

  public IterationSequenceGenerator getSequenceGenerator() {
    return sequenceGenerator;
  }

  public ConcurrentPseudoRandom getRandomizer() {
    return randomizer;
  }

  @Override
  public List<String> getDescription() {
    return Arrays.asList("Using " + caches.size() + " cache" + (caches.size() > 1 ? "s" : ""));
  }
}
