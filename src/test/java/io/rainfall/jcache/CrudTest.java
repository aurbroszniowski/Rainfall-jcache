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

import io.rainfall.generator.ByteArrayGenerator;
import org.ehcache.jcache.JCacheConfiguration;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import io.rainfall.Runner;
import io.rainfall.Scenario;
import io.rainfall.SyntaxException;
import io.rainfall.configuration.ConcurrencyConfig;
import io.rainfall.configuration.ReportingConfig;
import io.rainfall.generator.StringGenerator;
import io.rainfall.utils.SystemTest;

import java.util.concurrent.TimeUnit;

import javax.cache.Cache;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.Duration;
import javax.cache.expiry.ModifiedExpiryPolicy;

import static java.util.concurrent.TimeUnit.MINUTES;
import static io.rainfall.execution.Executions.nothingFor;
import static io.rainfall.execution.Executions.times;
import static io.rainfall.jcache.JCacheOperations.get;
import static io.rainfall.jcache.JCacheOperations.put;
import static io.rainfall.jcache.JCacheOperations.remove;
import static io.rainfall.jcache.operation.OperationWeight.OPERATION.GET;
import static io.rainfall.jcache.operation.OperationWeight.OPERATION.PUT;
import static io.rainfall.jcache.operation.OperationWeight.OPERATION.REMOVE;
import static io.rainfall.jcache.operation.OperationWeight.operation;
import static io.rainfall.unit.TimeDivision.seconds;

/**
 * @author Aurelien Broszniowski
 */

@Category(SystemTest.class)
public class CrudTest {

  @Test
  public void testLoad() throws SyntaxException {
    Cache one = Caching.getCachingProvider().getCacheManager().createCache("testSimpleLoad",
        new JCacheConfiguration<String, Byte>(new MutableConfiguration<String, Byte>().setStatisticsEnabled(true)
            .setExpiryPolicyFactory(ModifiedExpiryPolicy.factoryOf(new Duration(TimeUnit.MINUTES, 10)))
            .setStoreByValue(true)));

    CacheConfig<String, byte[]> cacheConfig = CacheConfig.<String, byte[]>cacheConfig()
        .caches(one)
        .using(StringGenerator.fixedLength(10), ByteArrayGenerator.fixedLength(128))
        .sequentially()
        .weights(operation(PUT, 0.10), operation(GET, 0.80), operation(REMOVE, 0.10));
    ConcurrencyConfig concurrency = ConcurrencyConfig.concurrencyConfig()
        .threads(4).timeout(5, MINUTES);
    ReportingConfig reporting = ReportingConfig.reportingConfig(ReportingConfig.text(), ReportingConfig.html());

    Scenario scenario = Scenario.scenario("Cache load")
        .exec(put())
        .exec(get())
        .exec(remove());

    Runner.setUp(scenario)
        .executed(times(10000000), nothingFor(10, seconds))
        .config(cacheConfig, concurrency, reporting)
//          .assertion(latencyTime(), isLessThan(1, seconds))
        .start();
  }

}