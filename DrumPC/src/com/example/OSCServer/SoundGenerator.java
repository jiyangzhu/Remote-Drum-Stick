package com.example.OSCServer;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

/**
 * Generate the drum sound
 * 
 * @author Bryce
 * @since 07-Nov-2014
 */
public class SoundGenerator {

	Sequencer player;
	Sequence sequence;
	Sequence sequence2;
	Track track;
	Track track2;
	
	MidiEvent noteOn;
	MidiEvent noteOff;

	public SoundGenerator() {
		try {
			// Get a Sequencer and open it
			player = MidiSystem.getSequencer();
			player.open();

			sequence = new Sequence(Sequence.PPQ, 4);
			sequence2 = new Sequence(Sequence.PPQ, 4);
			// Ask the Sequence for a Track.
			track = sequence.createTrack();
			track2 = sequence2.createTrack();
		} catch (MidiUnavailableException e) {
			e.printStackTrace();
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * @param note
	 *            - Pitch Byte [0-127]
	 */
	public void play(int note) {
		/*if(noteOn != null)
			track.remove(noteOn);
		if(noteOff != null)
			track.remove(noteOff);*/
		noteOn = makeEvent(152, 9, note, 50, 1);
		noteOff = makeEvent(152, 9, note, 0, 1);
		// NOTE ON
		track.add(noteOn);
		
		// NOTE OFF
		track.add(noteOff);
		
		try {
			player.setSequence(sequence);
		} catch (InvalidMidiDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		player.start();
		/*track.remove(noteOn);
		track.remove(noteOff);*/
		System.out.println("player.start");
		if(player.isRunning())
		{
			System.out.println("player is running");
		}
	}
	
	/**
	 * Close the sequencer
	 */
	private void close() {
		player.close();
	}

	/**
	 * 
	 * @param command
	 *            - 152 means NOTE ON
	 * @param channel
	 *            - MIDI Channels [0-15]. 9 is drummer
	 * @param note
	 *            - Pitch Byte [0-127]
	 * @param velocity
	 *            - MIDI Loudness [0-127], 0 means NOTE OFF
	 * @param tick
	 *            - the time-stamp for the event, in MIDI ticks
	 * @return
	 */
	public static MidiEvent makeEvent(int command, int channel, int note,
			int velocity, int tick) {
		MidiEvent event = null;

		ShortMessage a = new ShortMessage();
		try {
			a.setMessage(command, channel, note, velocity);
		} catch (InvalidMidiDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		event = new MidiEvent(a, tick);
		System.out.println("event's tick" + event.getTick());
		return event;
	}

}
