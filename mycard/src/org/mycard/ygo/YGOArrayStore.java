package org.mycard.ygo;

import java.util.ArrayList;
import java.util.List;

import org.mycard.R;

import android.content.res.Resources;
import android.util.SparseIntArray;

public class YGOArrayStore {
	public static final int  TYPE_MONSTER		=	    0x1;        //
	public static final int  TYPE_SPELL			=		0x2;		//
	public static final int  TYPE_TRAP			=	    0x4;		//
	public static final int  TYPE_NORMAL		=		0x10;		//
	public static final int  TYPE_EFFECT		=		0x20;		//
	public static final int  TYPE_FUSION		=		0x40;		//
	public static final int  TYPE_RITUAL		=		0x80;		//
	public static final int  TYPE_TRAPMONSTER 	=    	0x100;		//
	public static final int  TYPE_SPIRIT		=		0x200;		//
	public static final int  TYPE_UNION			=		0x400;		//
	public static final int  TYPE_DUAL			=		0x800;		//
	public static final int  TYPE_TUNER			=		0x1000;		//
	public static final int  TYPE_SYNCHRO		=		0x2000;	    //
	public static final int  TYPE_TOKEN			=		0x4000;		//
	public static final int  TYPE_QUICKPLAY		=		0x10000;	//
	public static final int  TYPE_CONTINUOUS	=		0x20000;	//
	public static final int  TYPE_EQUIP			=		0x40000;	//
	public static final int  TYPE_FIELD			=		0x80000;	//
	public static final int  TYPE_COUNTER		=		0x100000;	//
	public static final int  TYPE_FLIP			=		0x200000;	//
	public static final int  TYPE_TOON			=		0x400000;	//
	public static final int  TYPE_XYZ			=		0x800000;	//
	public static final int  TYPE_PENDULUM		=		0x1000000;  //
	
	
	public static final int  CARD_LEVEL_MASK = 0xFFFF;
	
	public static final List<SparseIntArray> sTypeMaps = new ArrayList<SparseIntArray>(3);
	
	static {
		SparseIntArray monsterArray = new SparseIntArray();
		//monster card
		monsterArray.append(ICardFilter.CARD_FILTER_TYPE_MONSTER_ALL, TYPE_MONSTER);
		monsterArray.append(ICardFilter.CARD_FILTER_TYPE_MONSTER_NORMAL, TYPE_NORMAL);
		monsterArray.append(ICardFilter.CARD_FILTER_TYPE_MONSTER_EFFECT, TYPE_EFFECT);
		monsterArray.append(ICardFilter.CARD_FILTER_TYPE_MONSTER_SYNRO, TYPE_SYNCHRO);
		monsterArray.append(ICardFilter.CARD_FILTER_TYPE_MONSTER_TUNER, TYPE_TUNER);
		monsterArray.append(ICardFilter.CARD_FILTER_TYPE_MONSTER_XYZ, TYPE_XYZ);
		monsterArray.append(ICardFilter.CARD_FILTER_TYPE_MONSTER_FUSION, TYPE_FUSION);
		monsterArray.append(ICardFilter.CARD_FILTER_TYPE_MONSTER_RITUAL, TYPE_RITUAL);
		monsterArray.append(ICardFilter.CARD_FILTER_TYPE_MONSTER_SPIRIT, TYPE_SPIRIT);
		monsterArray.append(ICardFilter.CARD_FILTER_TYPE_MONSTER_FLIP, TYPE_FLIP);
		monsterArray.append(ICardFilter.CARD_FILTER_TYPE_MONSTER_GEMINI, TYPE_DUAL);
		monsterArray.append(ICardFilter.CARD_FILTER_TYPE_MONSTER_UNION, TYPE_UNION);
		monsterArray.append(ICardFilter.CARD_FILTER_TYPE_MONSTER_TOKEN, TYPE_TOKEN);
		monsterArray.append(ICardFilter.CARD_FILTER_TYPE_MONSTER_TOON, TYPE_TOON);
		monsterArray.append(ICardFilter.CARD_FILTER_TYPE_MONSTER_PENDULUM, TYPE_PENDULUM);
		sTypeMaps.add(monsterArray);
		
		//spell card
		SparseIntArray spellArray = new SparseIntArray();
		spellArray.append(ICardFilter.CARD_FILTER_TYPE_SPELL_ALL, TYPE_SPELL);
		spellArray.append(ICardFilter.CARD_FILTER_TYPE_SPELL_QUICK, TYPE_QUICKPLAY);
		spellArray.append(ICardFilter.CARD_FILTER_TYPE_SPELL_CONTINUOUS, TYPE_CONTINUOUS);
		spellArray.append(ICardFilter.CARD_FILTER_TYPE_SPELL_EQUIP, TYPE_EQUIP);
		spellArray.append(ICardFilter.CARD_FILTER_TYPE_SPELL_FIELD, TYPE_FIELD);
		sTypeMaps.add(spellArray);
		
		//trap card
		SparseIntArray trapArray = new SparseIntArray();
		trapArray.append(ICardFilter.CARD_FILTER_TYPE_TRAP_ALL, TYPE_TRAP);
		trapArray.append(ICardFilter.CARD_FILTER_TYPE_TRAP_NOARMAL, TYPE_NORMAL);
		trapArray.append(ICardFilter.CARD_FILTER_TYPE_TRAP_CONTINUOUS, TYPE_CONTINUOUS);
		trapArray.append(ICardFilter.CARD_FILTER_TYPE_TRAP_COUNTER, TYPE_COUNTER);
		sTypeMaps.add(trapArray);
	}
	
	private String[] mOTArray;
	
	private String[] mRaceArray;
	
	private String[] mAttrArray;
	
	private String[] mTypeArray;
	
	private String[] mMonsterTypeArray;
	
	private String[] mTrapTypeArray;
	
	private String[] mSpellTypeArray;
	
	private String[] mMixCardTypeArray;
	
	private String mUnknown = "???";
	
	public YGOArrayStore(Resources res) {
		mTypeArray = res.getStringArray(R.array.card_type);
		mOTArray = res.getStringArray(R.array.card_limit);
		mRaceArray = res.getStringArray(R.array.card_race);
		mTypeArray = res.getStringArray(R.array.card_type);
		
		mAttrArray = res.getStringArray(R.array.card_attr);
		
		mMonsterTypeArray = res.getStringArray(R.array.card_monster_type);
		mSpellTypeArray = res.getStringArray(R.array.card_spell_type);
		mTrapTypeArray = res.getStringArray(R.array.card_trap_type);
		
		mMixCardTypeArray = res.getStringArray(R.array.card_mix_type);
	}
	
	public String getCardType(int code) {
		StringBuilder builder = new StringBuilder();
		int filter = 1, i = 0;
		builder.append('[');
		for(; filter != 0x2000000; filter <<= 1, ++i) {
			if((code & filter) > 0) {
				builder.append(mMixCardTypeArray[i]).append('|');
			}
		}
		if (builder.length() > 2) {
			builder.delete(builder.length() - 1, builder.length());
		    builder.append(']');
		} else {
			return mUnknown;
		}
		return builder.toString();
	}
	
	public String getCardRace(int code) {
		int x = 0;
		while (code > 1) {
			code >>= 1;
			x++;
		}
		if (x < mRaceArray.length) {
			return mRaceArray[++x];
		} else {
			return mUnknown;
		}
	}
	
	public String getCardAttr(int code) {
		int x = 0;
		while (code > 1) {
			code >>= 1;
			x++;
		}
		if (x < mAttrArray.length) {
			return mAttrArray[++x];
		} else {
			return mUnknown;
		}
	}
	
	public String getCardOT(int code) {
		if (code < 1 || code > mOTArray.length) {
			return mUnknown;
		} else {
			return mOTArray[code - 1];
		}
	}
}
