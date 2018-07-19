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

import java.util.Comparator;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.sleuthkit.datamodel.TimelineManager;

/**
 * Union(or) filter
 *
 * @param <SubFilterType> The type of the subfilters.
 */
abstract class AbstractUnionFilter<SubFilterType extends TimelineFilter> extends CompoundFilter<SubFilterType> {

	public AbstractUnionFilter(ObservableList<SubFilterType> subFilters) {
		super(subFilters);
	}

	public AbstractUnionFilter() {
		super(FXCollections.<SubFilterType>observableArrayList());
	}

	public void addSubFilter(SubFilterType subfilter) {
		addSubFilter(subfilter, Comparator.comparing(SubFilterType::getDisplayName));
	}

	protected void addSubFilter(SubFilterType subfilter, Comparator<SubFilterType> comparator) {
		if (getSubFilters().contains(subfilter) == false) {
			getSubFilters().add(subfilter);
		}
		getSubFilters().sort(comparator);
	}

	@Override
	public String getSQLWhere(TimelineManager manager) {
		String join = this.getSubFilters().stream()
				.map(filter -> filter.getSQLWhere(manager))
				.collect(Collectors.joining(" OR "));

		return join.isEmpty()
				? manager.getTrueLiteral()
				: "(" + join + ")";
	}
}