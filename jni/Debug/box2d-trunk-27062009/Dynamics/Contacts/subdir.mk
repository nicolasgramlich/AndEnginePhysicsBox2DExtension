################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
CPP_SRCS += \
../box2d-trunk-27062009/Dynamics/Contacts/b2CircleContact.cpp \
../box2d-trunk-27062009/Dynamics/Contacts/b2Contact.cpp \
../box2d-trunk-27062009/Dynamics/Contacts/b2ContactSolver.cpp \
../box2d-trunk-27062009/Dynamics/Contacts/b2EdgeAndCircleContact.cpp \
../box2d-trunk-27062009/Dynamics/Contacts/b2PolyAndCircleContact.cpp \
../box2d-trunk-27062009/Dynamics/Contacts/b2PolyAndEdgeContact.cpp \
../box2d-trunk-27062009/Dynamics/Contacts/b2PolyContact.cpp 

OBJS += \
./box2d-trunk-27062009/Dynamics/Contacts/b2CircleContact.o \
./box2d-trunk-27062009/Dynamics/Contacts/b2Contact.o \
./box2d-trunk-27062009/Dynamics/Contacts/b2ContactSolver.o \
./box2d-trunk-27062009/Dynamics/Contacts/b2EdgeAndCircleContact.o \
./box2d-trunk-27062009/Dynamics/Contacts/b2PolyAndCircleContact.o \
./box2d-trunk-27062009/Dynamics/Contacts/b2PolyAndEdgeContact.o \
./box2d-trunk-27062009/Dynamics/Contacts/b2PolyContact.o 

CPP_DEPS += \
./box2d-trunk-27062009/Dynamics/Contacts/b2CircleContact.d \
./box2d-trunk-27062009/Dynamics/Contacts/b2Contact.d \
./box2d-trunk-27062009/Dynamics/Contacts/b2ContactSolver.d \
./box2d-trunk-27062009/Dynamics/Contacts/b2EdgeAndCircleContact.d \
./box2d-trunk-27062009/Dynamics/Contacts/b2PolyAndCircleContact.d \
./box2d-trunk-27062009/Dynamics/Contacts/b2PolyAndEdgeContact.d \
./box2d-trunk-27062009/Dynamics/Contacts/b2PolyContact.d 


# Each subdirectory must supply rules for building sources it contributes
box2d-trunk-27062009/Dynamics/Contacts/%.o: ../box2d-trunk-27062009/Dynamics/Contacts/%.cpp
	@echo 'Building file: $<'
	@echo 'Invoking: Cygwin C++ Compiler'
	g++ -I"C:\Program Files\Java\jdk1.6\include\win32" -I"C:\Program Files\Java\jdk1.6\include" -O0 -g3 -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o"$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


