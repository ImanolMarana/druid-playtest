/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.druid.query.aggregation;

import com.google.common.collect.ImmutableList;
import org.apache.druid.query.filter.SelectorDimFilter;
import org.apache.druid.query.filter.TrueDimFilter;
import org.apache.druid.testing.InitializedNullHandlingTest;
import org.junit.Assert;
import org.junit.Test;

public class FilteredAggregatorFactoryTest extends InitializedNullHandlingTest
{
  @Test
  public void testSimpleNaming()
  {
    Assert.assertEquals("overrideName", new FilteredAggregatorFactory(
        new CountAggregatorFactory("foo"),
        TrueDimFilter.instance(),
        "overrideName"
    ).getName());
    Assert.assertEquals("delegateName", new FilteredAggregatorFactory(
        new CountAggregatorFactory("delegateName"),
        TrueDimFilter.instance(),
        ""
    ).getName());
    Assert.assertEquals("delegateName", new FilteredAggregatorFactory(
        new CountAggregatorFactory("delegateName"),
        TrueDimFilter.instance(),
        null
    ).getName());
  }

  @Test
  public void testNameOfCombiningFactory()
  {
    Assert.assertEquals("overrideName", new FilteredAggregatorFactory(
        new CountAggregatorFactory("foo"),
        TrueDimFilter.instance(),
        "overrideName"
    ).getCombiningFactory().getName());
    Assert.assertEquals("delegateName", new FilteredAggregatorFactory(
        new CountAggregatorFactory("delegateName"),
        TrueDimFilter.instance(),
        ""
    ).getCombiningFactory().getName());
    Assert.assertEquals("delegateName", new FilteredAggregatorFactory(
        new CountAggregatorFactory("delegateName"),
        TrueDimFilter.instance(),
        null
    ).getCombiningFactory().getName());
  }

  @Test
  public void testRequiredFields()
  {
    Assert.assertEquals(
        ImmutableList.of("x", "y"),
        new FilteredAggregatorFactory(
            new LongSumAggregatorFactory("x", "x"),
            new SelectorDimFilter("y", "wat", null)
        ).requiredFields()
    );
  }
}
