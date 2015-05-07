package com.au.unimelb.comp90020.multiplayer.concurrency;



import com.au.unimelb.comp90020.framework.util.LongLinkedList;
import com.au.unimelb.comp90020.multiplayer.networking.GameMulticastPeer;
import com.au.unimelb.comp90020.multiplayer.networking.Message;
import com.au.unimelb.comp90020.multiplayer.networking.Message.MessageType;
import com.au.unimelb.comp90020.multiplayer.networking.Process;



/*
 * The processes p1 ,p2 ... pN bear distinct numeric identifiers. 
 * They are assumed to possess communication channels to one another, 
 * and each process pi keeps a Lamport clock. Execution of the CS requests 
 * in this algorithm is always in the order of their timestamps.
 */
public class RAMutex extends Process implements Lock {

	int myts; //My timestamp
	LamportClock myClock = new LamportClock();
	LongLinkedList pendingQ = new LongLinkedList();
	int numReplies = 0;

	
	public RAMutex(GameMulticastPeer peer) {
		super(peer);
		setState(ProcessState.RELEASED); //on initialization
		myts = -1;
		System.out.println("Hello, I'm process: " + getMyId() + ", my state is: " + getMyState());
	}

	/*
	 * A process broadcasts a timestamped REQUEST message to all other processes to 
	 * request their permission to enter the critical section
	 */
	public synchronized void requestCS() {
		setState(ProcessState.WANTED);
		
		System.out.println("Hello, I'm process: " + getMyId() + ", my state is: " + getMyState() +", LC: "+myClock.getValue());
		myClock.tick();
		myts = myClock.getValue();
		//myts = myClock.getValue();
		broadcastMsg(MessageType.LOCK_REQUEST, myClock.getValue());
		numReplies = 0;
		while (numReplies < getNumberOfPlayers() - 1) //Wait until all have replied
			myWait(1000L);
		setState(ProcessState.HELD);
		System.out.println("Hello, I'm process: " + getMyId() + ", my state is: " + getMyState()  +", LC: "+myClock.getValue());
	}

	/*
	 * When process Pi exits the CS, replies to any queued requests in pendingQ, 
	 * in this case it empties pendingQ at the same time as well.
	 * 
	 */
	public synchronized void releaseCS() {
		//myts = Symbols.INFINITY; //--Is the same as state = RELEASED
		setState(ProcessState.RELEASED);
		myts = -1;
		System.out.println("Hello, I'm process: " + getMyId() + ", my state is: " + getMyState()  +", LC: "+myClock.getValue());
		while (!pendingQ.isEmpty()) {
			long pid = pendingQ.removeHead();
			sendMsg(Long.valueOf(pid), MessageType.LOCK_REPLY, myClock.getValue());
		}
	}

	/*
	 * A process sends a REPLY message to a process to give its permission to that process.
	 */
	public synchronized void handleMsg(Message m, long pid, String[] params) {
		//When a site receives a message, it updates its clock using the timestamp in the message
		int timeStamp = Integer.valueOf(params[1]);
		
		//When a site takes up a request for the CS for processing, it updates its local clock and assigns a timestamp to the request.
		myClock.receiveAction(pid, timeStamp);
		
		/*
		 * When process Pj receives a REQUEST message from process Pi, it sends a REPLY message to process Pi 
		 * if process Pj is neither requesting nor executing the CS, or if the process Pj is requesting 
		 * and Pi’s request’s timestamp is smaller than process Pj’s own request’s timestamp. 
		 * Otherwise, the reply is deferred and Pj updates its pendingQ.
		 */
		if (m.getType()==MessageType.LOCK_REQUEST) {
			//We do tie break by using ID's as well
			if ((getMyState() == ProcessState.RELEASED) || (timeStamp < myts) || ((timeStamp == myts) && (pid < myId)) ){
				sendMsg(pid, MessageType.LOCK_REPLY, myClock.getValue()); //Reply immediately
			}
			else{
				pendingQ.add(pid); //Queue request without replying
				System.out.println("ENCOLOOO");
			}
		} else if (m.getType()==MessageType.LOCK_REPLY) { //I got permission from process Pj
			numReplies++;
			if (numReplies == super.getNumberOfPlayers() - 1) //N-1
				notify(); // Execute CS only when all processes have answered with a REPLY
		}
	}

	@Override
	public void listen(Message m) {
		//System.out.println("RAMUTEX: "+m.getBody());
		String[] params = m.getBody().split(",");
		long pid = Long.valueOf(params[0]).longValue();
		if (m.getType()==MessageType.LOCK_REQUEST && pid!=myId){
			handleMsg(m, pid, params);
		}
		if (m.getType()==MessageType.LOCK_REPLY){
			handleMsg(m, pid, params);
		}
	}

	@Override
	public int getClockValue() {
		return this.myClock.getValue();
	}
}
