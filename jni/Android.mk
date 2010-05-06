# Copyright (C) 2009 The Android Open Source Project
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

# To enable Native Logging START
LOCAL_LDLIBS := -Lbuild/platforms/android-1.5/arch-arm/usr/lib -llog
# To enable Native Logging END 
	
LOCAL_MODULE    := andenginephysicsbox2dextension
LOCAL_SRC_FILES := org_anddev_andengine_physics_box2d_Box2DNativeWrapper.cpp \
	box2d-2.0.1/Common/b2Math.cpp \
	box2d-2.0.1/Common/b2Settings.cpp \
	box2d-2.0.1/Common/b2StackAllocator.cpp \
	box2d-2.0.1/Common/b2BlockAllocator.cpp \
	box2d-2.0.1/Dynamics/Contacts/b2CircleContact.cpp \
	box2d-2.0.1/Dynamics/Contacts/b2PolyContact.cpp \
	box2d-2.0.1/Dynamics/Contacts/b2Contact.cpp \
	box2d-2.0.1/Dynamics/Contacts/b2PolyAndCircleContact.cpp \
	box2d-2.0.1/Dynamics/Contacts/b2ContactSolver.cpp \
	box2d-2.0.1/Dynamics/b2Island.cpp \
	box2d-2.0.1/Dynamics/b2Body.cpp \
	box2d-2.0.1/Dynamics/b2ContactManager.cpp \
	box2d-2.0.1/Dynamics/b2WorldCallbacks.cpp \
	box2d-2.0.1/Dynamics/b2World.cpp \
	box2d-2.0.1/Dynamics/Joints/b2Joint.cpp \
	box2d-2.0.1/Dynamics/Joints/b2GearJoint.cpp \
	box2d-2.0.1/Dynamics/Joints/b2PrismaticJoint.cpp \
	box2d-2.0.1/Dynamics/Joints/b2MouseJoint.cpp \
	box2d-2.0.1/Dynamics/Joints/b2RevoluteJoint.cpp \
	box2d-2.0.1/Dynamics/Joints/b2DistanceJoint.cpp \
	box2d-2.0.1/Dynamics/Joints/b2PulleyJoint.cpp \
	box2d-2.0.1/Collision/b2BroadPhase.cpp \
	box2d-2.0.1/Collision/b2TimeOfImpact.cpp \
	box2d-2.0.1/Collision/b2Collision.cpp \
	box2d-2.0.1/Collision/b2Distance.cpp \
	box2d-2.0.1/Collision/b2CollidePoly.cpp \
	box2d-2.0.1/Collision/b2CollideCircle.cpp \
	box2d-2.0.1/Collision/Shapes/b2Shape.cpp \
	box2d-2.0.1/Collision/Shapes/b2PolygonShape.cpp \
	box2d-2.0.1/Collision/Shapes/b2CircleShape.cpp \
	box2d-2.0.1/Collision/b2PairManager.cpp

include $(BUILD_SHARED_LIBRARY)
