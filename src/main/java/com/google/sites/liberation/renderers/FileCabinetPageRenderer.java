/*
 * Copyright (C) 2009 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.sites.liberation.renderers;

import static com.google.gdata.util.common.base.Preconditions.checkNotNull;

import com.google.gdata.data.sites.AttachmentEntry;
import com.google.gdata.data.sites.FileCabinetPageEntry;
import com.google.sites.liberation.util.EntryStore;
import com.google.sites.liberation.util.XmlElement;

/**
 * An extension of BasePageRenderer which implements 
 * PageRenderer.renderSpecialContent to render the file cabinet section in a 
 * File Cabinet Page.
 * 
 * @author bsimon@google.com (Benjamin Simon)
 */
class FileCabinetPageRenderer extends BasePageRenderer<FileCabinetPageEntry> {
  
  /** 
   * Creates a new instance of FileCabinetPageRenderer for the given
   * FileCabinetPageEntry and EntryStore.
   */
  FileCabinetPageRenderer(FileCabinetPageEntry entry, EntryStore entryStore) {
    super(checkNotNull(entry), checkNotNull(entryStore));
  }
  
  /**
   * Renders the file cabinet section in the page.
   */
  @Override
  public XmlElement renderAdditionalContent() {
    if (attachments == null || attachments.size() == 0) {
      return null;
    }
    XmlElement table = new XmlElement("table");
    for(AttachmentEntry attachment : attachments) {
      XmlElement row = elementFactory.getEntryElement(attachment, "tr");
      XmlElement titleCell = new XmlElement("td").addElement(
          elementFactory.getTitleElement(attachment));
      row.addElement(titleCell);
      XmlElement updated = elementFactory.getUpdatedElement(attachment);
      XmlElement updatedCell = new XmlElement("td");
      row.addElement((new XmlElement("td")).addElement(updated));
      XmlElement author = elementFactory.getAuthorElement(attachment);
      row.addElement((new XmlElement("td")).addElement(author));
      XmlElement revision = elementFactory.getRevisionElement(attachment);
      row.addElement((new XmlElement("td")).addText("(Version ")
          .addElement(revision).addText(")"));
      table.addElement(row);
    }
    return table;
  }
  
  /**
   * Overrides the normal renderAttachments method to return {@code null} since
   * a File Cabinet Page does not contain the normal attachments section.
   */
  @Override
  public XmlElement renderAttachments() {
    return null;
  }
}