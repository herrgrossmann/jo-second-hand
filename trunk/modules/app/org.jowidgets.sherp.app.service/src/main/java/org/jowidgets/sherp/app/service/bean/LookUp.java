/*
 * Copyright (c) 2012, grossmann
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 * * Neither the name of the jo-widgets.org nor the
 *   names of its contributors may be used to endorse or promote products
 *   derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL jo-widgets.org BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 */
package org.jowidgets.sherp.app.service.bean;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Index;
import org.jowidgets.sherp.app.common.bean.ILookUp;
import org.jowidgets.sherp.app.common.dto.LookUpDisplayFormat;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
public class LookUp extends Bean implements ILookUp {

	@Basic
	@Index(name = "NameIndex")
	private String name;

	@Basic
	private String label;

	@Basic
	private Boolean hasNullValue;

	@Basic
	private LookUpDisplayFormat defaultDisplayFormat;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "lookUp")
	@BatchSize(size = 1000)
	@OrderBy("key")
	private final List<LookUpElement> lookUpElements = new LinkedList<LookUpElement>();

	public List<LookUpElement> getLookUpElements() {
		return lookUpElements;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(final String name) {
		this.name = name;
	}

	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public void setLabel(final String label) {
		this.label = label;
	}

	@Override
	public Boolean getHasNullValue() {
		return hasNullValue;
	}

	@Override
	public void setHasNullValue(final Boolean hasNullValue) {
		this.hasNullValue = hasNullValue;
	}

	@Override
	public LookUpDisplayFormat getDefaultDisplayFormat() {
		return defaultDisplayFormat;
	}

	@Override
	public void setDefaultDisplayFormat(final LookUpDisplayFormat defaultDisplayFormat) {
		this.defaultDisplayFormat = defaultDisplayFormat;
	}

}
