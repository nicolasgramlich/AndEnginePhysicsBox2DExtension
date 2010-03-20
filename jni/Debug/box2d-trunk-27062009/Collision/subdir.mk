################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
CPP_SRCS += \
../box2d-trunk-27062009/Collision/b2BroadPhase.cpp \
../box2d-trunk-27062009/Collision/b2CollideCircle.cpp \
../box2d-trunk-27062009/Collision/b2CollideEdge.cpp \
../box2d-trunk-27062009/Collision/b2CollidePoly.cpp \
../box2d-trunk-27062009/Collision/b2Collision.cpp \
../box2d-trunk-27062009/Collision/b2Distance.cpp \
../box2d-trunk-27062009/Collision/b2DynamicTree.cpp \
../box2d-trunk-27062009/Collision/b2PairManager.cpp \
../box2d-trunk-27062009/Collision/b2TimeOfImpact.cpp 

OBJS += \
./box2d-trunk-27062009/Collision/b2BroadPhase.o \
./box2d-trunk-27062009/Collision/b2CollideCircle.o \
./box2d-trunk-27062009/Collision/b2CollideEdge.o \
./box2d-trunk-27062009/Collision/b2CollidePoly.o \
./box2d-trunk-27062009/Collision/b2Collision.o \
./box2d-trunk-27062009/Collision/b2Distance.o \
./box2d-trunk-27062009/Collision/b2DynamicTree.o \
./box2d-trunk-27062009/Collision/b2PairManager.o \
./box2d-trunk-27062009/Collision/b2TimeOfImpact.o 

CPP_DEPS += \
./box2d-trunk-27062009/Collision/b2BroadPhase.d \
./box2d-trunk-27062009/Collision/b2CollideCircle.d \
./box2d-trunk-27062009/Collision/b2CollideEdge.d \
./box2d-trunk-27062009/Collision/b2CollidePoly.d \
./box2d-trunk-27062009/Collision/b2Collision.d \
./box2d-trunk-27062009/Collision/b2Distance.d \
./box2d-trunk-27062009/Collision/b2DynamicTree.d \
./box2d-trunk-27062009/Collision/b2PairManager.d \
./box2d-trunk-27062009/Collision/b2TimeOfImpact.d 


# Each subdirectory must supply rules for building sources it contributes
box2d-trunk-27062009/Collision/%.o: ../box2d-trunk-27062009/Collision/%.cpp
	@echo 'Building file: $<'
	@echo 'Invoking: Cygwin C++ Compiler'
	g++ -I"C:\Program Files\Java\jdk1.6\include\win32" -I"C:\Program Files\Java\jdk1.6\include" -O0 -g3 -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o"$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


