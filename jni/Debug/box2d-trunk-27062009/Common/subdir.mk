################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
CPP_SRCS += \
../box2d-trunk-27062009/Common/b2BlockAllocator.cpp \
../box2d-trunk-27062009/Common/b2Math.cpp \
../box2d-trunk-27062009/Common/b2Settings.cpp \
../box2d-trunk-27062009/Common/b2StackAllocator.cpp 

OBJS += \
./box2d-trunk-27062009/Common/b2BlockAllocator.o \
./box2d-trunk-27062009/Common/b2Math.o \
./box2d-trunk-27062009/Common/b2Settings.o \
./box2d-trunk-27062009/Common/b2StackAllocator.o 

CPP_DEPS += \
./box2d-trunk-27062009/Common/b2BlockAllocator.d \
./box2d-trunk-27062009/Common/b2Math.d \
./box2d-trunk-27062009/Common/b2Settings.d \
./box2d-trunk-27062009/Common/b2StackAllocator.d 


# Each subdirectory must supply rules for building sources it contributes
box2d-trunk-27062009/Common/%.o: ../box2d-trunk-27062009/Common/%.cpp
	@echo 'Building file: $<'
	@echo 'Invoking: Cygwin C++ Compiler'
	g++ -I"C:\Program Files\Java\jdk1.6\include\win32" -I"C:\Program Files\Java\jdk1.6\include" -O0 -g3 -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o"$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


