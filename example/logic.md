int main(){
	

	int switchPin = callMPUPIN(3);
	int interruptPin = callMPUPIN(2)

	int interruptResult = interrtuptNotification(interruptPin);

	if (interruptResult == 1){
		callProcessor();
	}else if (interruptResult == 0){
		wait();
	}
}

int interrtuptNotification(int interruptPin){
	
	fdskfjdsfosldfdsn;fdsks;dfkjn;dsfdnfksd;sjdla
}

void callProcessor(){
	
	//total time 10ms 1000,000 us
	offSwitch(switchPin);

	0-128

	0-> 0us
	1-> 75us
	2-> 150us
	.
	.
	.
	.
	.
	.
	128-> 1000,000us

	s 1 -> 0 us
	s 2 -> 1875 us
	s 3 -> 5625 us
	s 4 -> 7500 us
	s 5 -> 10000 us

	int masterSwitch = s3;//5625us

	time = 0;
	while (time > masterSwitch){
		time = time+75;
		sleep(75);
	}

	onSwitch(switchPin);

}
