APPNAME = Ulong64Program
CXXFLAGS =	-O2 -g -Wall -fmessage-length=0

OBJS =		${APPNAME}.o

LIBS = -lrt
ifeq "$(APPNAME)" "Ulong64Program"
CFLAGS += -D__APPLE__
endif  
$(APPNAME):	$(OBJS)
	g++ -o $(APPNAME) $(OBJS) $(LIBS)
	./$(APPNAME)

all:	$(APPNAME)

clean:
	rm -f $(OBJS) $(APPNAME)
