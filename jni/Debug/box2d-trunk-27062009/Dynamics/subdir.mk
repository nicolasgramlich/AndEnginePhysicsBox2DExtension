################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
CPP_SRCS += \
../box2d-trunk-27062009/Dynamics/b2Body.cpp \
../box2d-trunk-27062009/Dynamics/b2ContactManager.cpp \
../box2d-trunk-27062009/Dynamics/b2EdgeChain.cpp \
../box2d-trunk-27062009/Dynamics/b2Fixture.cpp \
../box2d-trunk-27062009/Dynamics/b2Island.cpp \
../box2d-trunk-27062009/Dynamics/b2World.cpp \
../box2d-trunk-27062009/Dynamics/b2WorldCallbacks.cpp 

OBJS += \
./box2d-trunk-27062009/Dynamics/b2Body.o \
./box2d-trunk-27062009/Dynamics/b2ContactManager.o \
./box2d-trunk-27062009/Dynamics/b2EdgeChain.o \
./box2d-trunk-27062009/Dynamics/b2Fixture.o \
./box2d-trunk-27062009/Dynamics/b2Island.o \
./box2d-trunk-27062009/Dynamics/b2World.o \
./box2d-trunk-27062009/Dynamics/b2WorldCallbacks.o 

CPP_DEPS += \
./box2d-trunk-27062009/Dynamics/b2Body.d \
./box2d-trunk-27062009/Dynamics/b2ContactManager.d \
./box2d-trunk-27062009/Dynamics/b2EdgeChain.d \
./box2d-trunk-27062009/Dynamics/b2Fixture.d \
./box2d-trunk-27062009/Dynamics/b2Island.d \
./box2d-trunk-27062009/Dynamics/b2World.d \
./box2d-trunk-27062009/Dynamics/b2WorldCallbacks.d 


# Each subdirectory must supply rules for building sources it contributes
box2d-trunk-27062009/Dynamics/%.o: ../box2d-trunk-27062009/Dynamics/%.cpp
	@echo 'Building file: $<'
	@echo 'Invoking: Cygwin C++ Compiler'
	g++ -I"C:\Program Files\Java\jdk1.6\include\win32" -I"C:\Program Files\Java\jdk1.6\include" -O0 -g3 -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o"$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


