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

package org.jowidgets.sherp.app.service.entity;

import org.jowidgets.cap.common.api.service.IReaderService;
import org.jowidgets.cap.service.api.entity.IBeanEntityBluePrint;
import org.jowidgets.cap.service.api.entity.IBeanEntityLinkBluePrint;
import org.jowidgets.cap.service.jpa.api.query.ICriteriaQueryCreatorBuilder;
import org.jowidgets.cap.service.jpa.api.query.JpaQueryToolkit;
import org.jowidgets.cap.service.jpa.tools.entity.JpaEntityServiceBuilderWrapper;
import org.jowidgets.service.api.IServiceRegistry;
import org.jowidgets.sherp.app.common.bean.ILookUpElement;
import org.jowidgets.sherp.app.common.entity.SecondHandEntityIds;
import org.jowidgets.sherp.app.service.bean.Commodity;
import org.jowidgets.sherp.app.service.bean.Customer;
import org.jowidgets.sherp.app.service.bean.LookUp;
import org.jowidgets.sherp.app.service.bean.LookUpElement;
import org.jowidgets.sherp.app.service.descriptor.CommodityDtoDescriptorBuilder;
import org.jowidgets.sherp.app.service.descriptor.CustomerDtoDescriptorBuilder;
import org.jowidgets.sherp.app.service.descriptor.LookUpDtoDescriptorBuilder;
import org.jowidgets.sherp.app.service.descriptor.LookUpElementDtoDescriptorBuilder;

public final class SecondHandEntityServiceBuilder extends JpaEntityServiceBuilderWrapper {

	public SecondHandEntityServiceBuilder(final IServiceRegistry registry) {
		super(registry);

		//ICustomer
		IBeanEntityBluePrint bp = addEntity().setEntityId(SecondHandEntityIds.CUSTOMER).setBeanType(Customer.class);
		bp.setDtoDescriptor(new CustomerDtoDescriptorBuilder());

		//ICommodity
		bp = addEntity().setEntityId(SecondHandEntityIds.COMMODITY).setBeanType(Commodity.class);
		bp.setDtoDescriptor(new CommodityDtoDescriptorBuilder());

		//ILookUp
		bp = addEntity().setEntityId(SecondHandEntityIds.LOOK_UP).setBeanType(LookUp.class);
		bp.setDtoDescriptor(new LookUpDtoDescriptorBuilder());
		addLookUpLookUpElementLinkDescriptor(bp);

		//Linked look up elements of look up
		bp = addEntity().setEntityId(SecondHandEntityIds.LINKED_LOOK_UP_ELEMENTS_OF_LOOK_UP);
		bp.setBeanType(LookUpElement.class);
		bp.setDtoDescriptor(new LookUpElementDtoDescriptorBuilder());
		bp.setReaderService(createLookUpElementsOfLookUpReader());
		bp.setProperties(ILookUpElement.ALL_PROPERTIES);

	}

	private void addLookUpLookUpElementLinkDescriptor(final IBeanEntityBluePrint entityBp) {
		final IBeanEntityLinkBluePrint bp = entityBp.addLink();
		bp.setLinkEntityId(SecondHandEntityIds.LINKED_LOOK_UP_ELEMENTS_OF_LOOK_UP);
		bp.setLinkBeanType(LookUpElement.class);
		bp.setLinkedEntityId(SecondHandEntityIds.LINKED_LOOK_UP_ELEMENTS_OF_LOOK_UP);
		bp.setSourceProperties(LookUpElement.LOOK_UP_ID_PROPERTY);
		bp.setLinkDeleterService(null);
	}

	private IReaderService<Void> createLookUpElementsOfLookUpReader() {
		final ICriteriaQueryCreatorBuilder<Void> queryBuilder = JpaQueryToolkit.criteriaQueryCreatorBuilder(LookUpElement.class);
		queryBuilder.setParentPropertyPath("lookUp");
		return getServiceFactory().readerService(LookUpElement.class, queryBuilder.build(), ILookUpElement.ALL_PROPERTIES);
	}
}
