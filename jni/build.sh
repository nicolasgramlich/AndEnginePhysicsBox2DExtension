#!/bin/bash

NDK_DIRECTORY="/Users/ngramlich/SDKs/Android/ndk/r8/"
PROJECT_DIRECTORY="/Users/ngramlich/Workspace/gdk/graphic_engines/AndEngine/AndEnginePhysicsBox2DExtension/"

# Run build:
pushd ${PROJECT_DIRECTORY} > /dev/null
${NDK_DIRECTORY}ndk-build NDK_DEBUG=1

# Clean temporary files:
rm -rf ${PROJECT_DIRECTORY}obj
find . -name gdbserver -print0 | xargs -0 rm -rf
find . -name gdb.setup -print0 | xargs -0 rm -rf

popd > /dev/null