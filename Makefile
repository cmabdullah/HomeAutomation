APPNAME = VoltageRegulatorFinalShape
CXXFLAGS =	-O2 -g -Wall -fmessage-length=0

OBJS =		${APPNAME}.o

ifeq "$(APPNAME)" "VoltageRegulatorFinalShape"
LDFLAGS = -lcurl -lpthread
CFLAGS += -D__APPLE__
endif  
$(APPNAME):	$(OBJS)
	g++ -o $(APPNAME) $(OBJS)  $(LDFLAGS)
	./$(APPNAME)

all:	$(APPNAME)

clean:
	rm -f $(OBJS) $(APPNAME)
