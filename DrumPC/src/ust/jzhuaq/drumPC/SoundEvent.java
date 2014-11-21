package ust.jzhuaq.drumPC;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Track;

import ust.jzhuaq.drumPC.Util.Constants;


/**
 * SoundEvent object
 * @author Bryce
 * @since 16-Nov-2014
 */
public class SoundEvent {

	private int commandON; // Status + Channel
	private int commandOFF;
	private int channel; // MIDI Channels [0-15]. 9 is drummer
	private int note; // Pitch Byte [0-127]
	private int velocity; // MIDI Loudness [0-127]
	private int tick;
	private long duration; // The length of time that this sound last.
							// millisecond

	private Synthesizer synthesizer;
	private Receiver receiver;
	private MidiChannel midiChannel;
	private ShortMessage shortMsgON;
	private ShortMessage shortMsgOFF;

	/**
	 * COMMAND_STATUS_NOTE_ON: Must added with channel number before used<br/>
	 * A complete command = COMMAND_STATUS_NOTE_ON + channel#
	 */
	public final static int COMMAND_STATUS_NOTE_ON = 143;

	public final static int COMMAND_DRUM_NOTE_ON = 152;
	public final static int COMMAND_DRUM_NOTE_OFF = 136;

	/**
	 * Differ between a note-on command and it respective note-off command<br/>
	 * note-off command = note-on command + COMMAND_ON_TO_OFF
	 */
	public final static int COMMAND_ON_TO_OFF = -16;

	public final static int CHANNEL_DRUM = 9;

	public final static int NOTE_DRUM_0 = Constants.NOTE_CYMBAL_RIDE_1;
	public final static int NOTE_DRUM_1 = Constants.NOTE_DRUM_TOM_LOW;
	public final static int NOTE_DRUM_2 = Constants.NOTE_DRUM_TOM_HIGH;
	public final static int NOTE_DRUM_3 = Constants.NOTE_HI_HAT_OPEN;
	public final static int NOTE_DRUM_4 = Constants.NOTE_DRUM_SNARE_ACOUSTIC;
	public final static int NOTE_DRUM_5 = Constants.NOTE_DRUM_BASS_DRUM_ACOUSTIC;
	public final static int NOTE_DRUM_6 = Constants.NOTE_DRUM_FLOOR_TOM_HIGH;

	public final static int VELOCITY_HIGH = 127;
	public final static int VELOCITY_MIDDLE = 70;
	public final static int VELOCITY_LOW = 30;

	public final static int TICK_DEFAULT = 1;

	public final static int DURATION_DEFAULT = 100;

	/**
	 * Default SoundEvent<br/>
	 * channel = CHANNEL_DRUM;<br/>
	 * note = NOTE_DRUM_STANDARD;<br/>
	 * velocity = VELOCITY_HIGH;<br/>
	 */
	public SoundEvent() {
		this(CHANNEL_DRUM, NOTE_DRUM_0, VELOCITY_HIGH);
	}

	/**
	 * 
	 * @param note
	 *            Pitch Byte [0-127]
	 */
	public SoundEvent(int note) {
		this(CHANNEL_DRUM, note, VELOCITY_HIGH);
	}

	/**
	 * 
	 * @param note
	 *            Pitch Byte [0-127]
	 * @param velocity
	 *            MIDI Loudness [0-127]
	 */
	public SoundEvent(int note, int velocity) {
		this(CHANNEL_DRUM, note, velocity);

	}

	/**
	 * 
	 * @param channel
	 *            MIDI Channels [0-15]. 9 is drummer
	 * @param note
	 *            Pitch Byte [0-127]
	 * @param velocity
	 *            MIDI Loudness [0-127]
	 */
	public SoundEvent(int channel, int note, int velocity) {
		this.channel = channel;
		this.commandON = COMMAND_STATUS_NOTE_ON + this.channel;
		this.commandOFF = this.commandON + COMMAND_ON_TO_OFF;
		this.note = note;
		this.velocity = velocity;
		this.tick = TICK_DEFAULT;
		this.duration = DURATION_DEFAULT;
		try {
			this.synthesizer = MidiSystem.getSynthesizer();
			this.receiver = MidiSystem.getReceiver();
		} catch (MidiUnavailableException e) {
			e.printStackTrace();
		}
		this.midiChannel = synthesizer.getChannels()[this.channel];
		try {
			this.shortMsgON = new ShortMessage(this.commandON, this.channel,
					this.note, this.velocity);
			this.shortMsgOFF = new ShortMessage(this.commandOFF, this.channel,
					this.note, this.velocity);
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
		}
	}

	public void play() {
		noteOn();
		noteOff();
		new Thread(new Runnable() {

			@Override
			public void run() {
				// Thread.sleep(duration);
				// noteOff();

			}
		}).start();
	}

	private void noteOn() {
		this.receiver.send(this.shortMsgON, -1);
	}

	private void noteOff() {
		this.receiver.send(this.shortMsgOFF, -1);
		System.out.println("noteOff");
	}

}
