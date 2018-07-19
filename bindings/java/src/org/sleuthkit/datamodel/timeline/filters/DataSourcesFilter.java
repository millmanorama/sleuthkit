/*
 * Sleuth Kit Data Model
 *
 * Copyright 2018 Basis Technology Corp.
 * Contact: carrier <at> sleuthkit <dot> org
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
package org.sleuthkit.datamodel.timeline.filters;

/**
 * union of {@link DataSourceFilter}s
 */
public class DataSourcesFilter extends AbstractUnionFilter<DataSourceFilter> {

	public DataSourcesFilter() {
//		disabledPropertyOverride = Bindings.or(super.disabledProperty(), Bindings.size(getSubFilters()).lessThanOrEqualTo(1));
//		activePropertyOverride = super.activeProperty().and(Bindings.not(disabledPropertyOverride));
	}

	@Override
	public DataSourcesFilter copyOf() {
		final DataSourcesFilter filterCopy = new DataSourcesFilter();
		//add a copy of each subfilter
		getSubFilters().forEach(dataSourceFilter -> filterCopy.addSubFilter(dataSourceFilter.copyOf()));
		return filterCopy;
	}

	@Override
	public String getDisplayName() {
		return BundleUtils.getBundle().getString("DataSourcesFilter.displayName.text");
	}

	 
}