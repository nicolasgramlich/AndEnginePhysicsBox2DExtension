################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
CPP_SRCS += \
../box2d-trunk-27062009/Dynamics/Joints/b2DistanceJoint.cpp \
../box2d-trunk-27062009/Dynamics/Joints/b2FixedJoint.cpp \
../box2d-trunk-27062009/Dynamics/Joints/b2GearJoint.cpp \
../box2d-trunk-27062009/Dynamics/Joints/b2Joint.cpp \
../box2d-trunk-27062009/Dynamics/Joints/b2LineJoint.cpp \
../box2d-trunk-27062009/Dynamics/Joints/b2MouseJoint.cpp \
../box2d-trunk-27062009/Dynamics/Joints/b2PrismaticJoint.cpp \
../box2d-trunk-27062009/Dynamics/Joints/b2PulleyJoint.cpp \
../box2d-trunk-27062009/Dynamics/Joints/b2RevoluteJoint.cpp 

OBJS += \
./box2d-trunk-27062009/Dynamics/Joints/b2DistanceJoint.o \
./box2d-trunk-27062009/Dynamics/Joints/b2FixedJoint.o \
./box2d-trunk-27062009/Dynamics/Joints/b2GearJoint.o \
./box2d-trunk-27062009/Dynamics/Joints/b2Joint.o \
./box2d-trunk-27062009/Dynamics/Joints/b2LineJoint.o \
./box2d-trunk-27062009/Dynamics/Joints/b2MouseJoint.o \
./box2d-trunk-27062009/Dynamics/Joints/b2PrismaticJoint.o \
./box2d-trunk-27062009/Dynamics/Joints/b2PulleyJoint.o \
./box2d-trunk-27062009/Dynamics/Joints/b2RevoluteJoint.o 

CPP_DEPS += \
./box2d-trunk-27062009/Dynamics/Joints/b2DistanceJoint.d \
./box2d-trunk-27062009/Dynamics/Joints/b2FixedJoint.d \
./box2d-trunk-27062009/Dynamics/Joints/b2GearJoint.d \
./box2d-trunk-27062009/Dynamics/Joints/b2Joint.d \
./box2d-trunk-27062009/Dynamics/Joints/b2LineJoint.d \
./box2d-trunk-27062009/Dynamics/Joints/b2MouseJoint.d \
./box2d-trunk-27062009/Dynamics/Joints/b2PrismaticJoint.d \
./box2d-trunk-27062009/Dynamics/Joints/b2PulleyJoint.d \
./box2d-trunk-27062009/Dynamics/Joints/b2RevoluteJoint.d 


# Each subdirectory must supply rules for building sources it contributes
box2d-trunk-27062009/Dynamics/Joints/%.o: ../box2d-trunk-27062009/Dynamics/Joints/%.cpp
	@echo 'Building file: $<'
	@echo 'Invoking: Cygwin C++ Compiler'
	g++ -I"C:\Program Files\Java\jdk1.6\include\win32" -I"C:\Program Files\Java\jdk1.6\include" -O0 -g3 -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o"$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


