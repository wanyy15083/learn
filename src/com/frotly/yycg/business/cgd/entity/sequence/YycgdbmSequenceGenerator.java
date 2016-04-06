package com.frotly.yycg.business.cgd.entity.sequence;

import java.io.Serializable;

import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.SequenceGenerator;

import com.frotly.yycg.util.MyUtil;

/**
 * 自定义序列生成器：4位年+6位流水号
 * @author Frotly
 *
 */
public class YycgdbmSequenceGenerator extends SequenceGenerator {

	@Override
	public Serializable generate(SessionImplementor session, Object obj) {
		Serializable sequence = super.generate(session, obj);
		String sequence_string = String.valueOf(sequence);
		String year = MyUtil.get_YYYY(MyUtil.getDate());
		sequence_string = sequence_string + year;
		return Integer.parseInt(sequence_string);
	}
	
}
