################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
CPP_SRCS += \
../box2d-trunk-27062009/Collision/Shapes/b2CircleShape.cpp \
../box2d-trunk-27062009/Collision/Shapes/b2EdgeShape.cpp \
../box2d-trunk-27062009/Collision/Shapes/b2PolygonShape.cpp \
../box2d-trunk-27062009/Collision/Shapes/b2Shape.cpp 

OBJS += \
./box2d-trunk-27062009/Collision/Shapes/b2CircleShape.o \
./box2d-trunk-27062009/Collision/Shapes/b2EdgeShape.o \
./box2d-trunk-27062009/Collision/Shapes/b2PolygonShape.o \
./box2d-trunk-27062009/Collision/Shapes/b2Shape.o 

CPP_DEPS += \
./box2d-trunk-27062009/Collision/Shapes/b2CircleShape.d \
./box2d-trunk-27062009/Collision/Shapes/b2EdgeShape.d \
./box2d-trunk-27062009/Collision/Shapes/b2PolygonShape.d \
./box2d-trunk-27062009/Collision/Shapes/b2Shape.d 


# Each subdirectory must supply rules for building sources it contributes
box2d-trunk-27062009/Collision/Shapes/%.o: ../box2d-trunk-27062009/Collision/Shapes/%.cpp
	@echo 'Building file: $<'
	@echo 'Invoking: Cygwin C++ Compiler'
	g++ -I"C:\Program Files\Java\jdk1.6\include\win32" -I"C:\Program Files\Java\jdk1.6\include" -O0 -g3 -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o"$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


