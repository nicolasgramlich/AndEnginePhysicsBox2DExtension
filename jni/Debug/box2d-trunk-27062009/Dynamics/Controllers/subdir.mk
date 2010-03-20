################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
CPP_SRCS += \
../box2d-trunk-27062009/Dynamics/Controllers/b2BuoyancyController.cpp \
../box2d-trunk-27062009/Dynamics/Controllers/b2ConstantAccelController.cpp \
../box2d-trunk-27062009/Dynamics/Controllers/b2ConstantForceController.cpp \
../box2d-trunk-27062009/Dynamics/Controllers/b2Controller.cpp \
../box2d-trunk-27062009/Dynamics/Controllers/b2GravityController.cpp \
../box2d-trunk-27062009/Dynamics/Controllers/b2TensorDampingController.cpp 

OBJS += \
./box2d-trunk-27062009/Dynamics/Controllers/b2BuoyancyController.o \
./box2d-trunk-27062009/Dynamics/Controllers/b2ConstantAccelController.o \
./box2d-trunk-27062009/Dynamics/Controllers/b2ConstantForceController.o \
./box2d-trunk-27062009/Dynamics/Controllers/b2Controller.o \
./box2d-trunk-27062009/Dynamics/Controllers/b2GravityController.o \
./box2d-trunk-27062009/Dynamics/Controllers/b2TensorDampingController.o 

CPP_DEPS += \
./box2d-trunk-27062009/Dynamics/Controllers/b2BuoyancyController.d \
./box2d-trunk-27062009/Dynamics/Controllers/b2ConstantAccelController.d \
./box2d-trunk-27062009/Dynamics/Controllers/b2ConstantForceController.d \
./box2d-trunk-27062009/Dynamics/Controllers/b2Controller.d \
./box2d-trunk-27062009/Dynamics/Controllers/b2GravityController.d \
./box2d-trunk-27062009/Dynamics/Controllers/b2TensorDampingController.d 


# Each subdirectory must supply rules for building sources it contributes
box2d-trunk-27062009/Dynamics/Controllers/%.o: ../box2d-trunk-27062009/Dynamics/Controllers/%.cpp
	@echo 'Building file: $<'
	@echo 'Invoking: Cygwin C++ Compiler'
	g++ -I"C:\Program Files\Java\jdk1.6\include\win32" -I"C:\Program Files\Java\jdk1.6\include" -O0 -g3 -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o"$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


