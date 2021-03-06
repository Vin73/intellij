/*
 * Copyright 2016 The Bazel Authors. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.idea.blaze.android.sync;

import com.google.common.collect.ImmutableList;
import com.google.idea.blaze.android.sync.model.BlazeAndroidSyncData;
import com.google.idea.blaze.base.ideinfo.LibraryArtifact;
import com.google.idea.blaze.base.model.BlazeLibrary;
import com.google.idea.blaze.base.model.BlazeProjectData;
import com.google.idea.blaze.base.sync.libraries.LibrarySource;
import com.google.idea.blaze.java.sync.model.BlazeJarLibrary;
import java.util.List;

class BlazeAndroidLibrarySource extends LibrarySource.Adapter {
  private final BlazeProjectData blazeProjectData;

  BlazeAndroidLibrarySource(BlazeProjectData blazeProjectData) {
    this.blazeProjectData = blazeProjectData;
  }

  @Override
  public List<BlazeLibrary> getLibraries() {
    BlazeAndroidSyncData syncData = blazeProjectData.syncState.get(BlazeAndroidSyncData.class);
    if (syncData == null) {
      return ImmutableList.of();
    }
    ImmutableList.Builder<BlazeLibrary> libraries = ImmutableList.builder();
    if (syncData.importResult.resourceLibrary != null) {
      libraries.add(syncData.importResult.resourceLibrary);
    }
    if (syncData.importResult.javacJar != null) {
      libraries.add(
          new BlazeJarLibrary(
              new LibraryArtifact(null, syncData.importResult.javacJar, ImmutableList.of())));
    }
    return libraries.build();
  }
}
