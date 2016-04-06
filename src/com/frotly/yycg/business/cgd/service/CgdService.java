package com.frotly.yycg.business.cgd.service;

import java.util.List;

import com.frotly.yycg.business.cgd.entity.Yycgd;
import com.frotly.yycg.business.cgd.entity.Yycgdmx;
import com.frotly.yycg.business.cgd.pojo.YycgdCustom;
import com.frotly.yycg.business.cgd.pojo.YycgdQueryVo;
import com.frotly.yycg.business.ypml.entity.Ypxx;

/**
 * 采购单业务接口
 * @author Frotly
 *
 */
public interface CgdService {

	/**
	 * 创建采购单
	 * @param useryyid
	 * @param yycgdCustom
	 * @return 采购单id
	 */
	public Integer insertYycgd(String useryyid,YycgdCustom yycgdCustom);
	/**
	 * 根据采购单id查询采购单
	 * @param yycgdid
	 * @return
	 */
	public Yycgd findYycgdById(Integer yycgdid);
	
	/**
	 * 分页查询所有采购单
	 * @param useryyId
	 * @param yycgdQueryVo
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	public List<Yycgd> findYycgdList(String useryyId,YycgdQueryVo yycgdQueryVo,int firstResult,int maxResults);
	/**
	 * 查询采购单总数
	 * @param useryyId
	 * @param yycgdQueryVo
	 * @return
	 */
	public Long findYycgdListCount(String useryyId,YycgdQueryVo yycgdQueryVo);
	/**
	 * 分页查询药品信息
	 * @param yycgdQueryVo
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	public List<Ypxx> findYpxxList(YycgdQueryVo yycgdQueryVo,int firstResult,int maxResults);
	/**
	 * 查询药品总数
	 * @param yycgdQueryVo
	 * @return
	 */
	public Long findYpxxListCount(YycgdQueryVo yycgdQueryVo);
	/**
	 * 更新采购单基本信息
	 * @param yycgd
	 */
	public void updateYycgd(Yycgd yycgd);
	/**
	 * 添加采购单明细
	 * @param yycgdid
	 * @param ypxxids 药品id串
	 */
	public void insertYycgdmx(Integer yycgdid,List<String> ypxxids);
	/**
	 * 分页查询采购单明细
	 * @param yycgdid
	 * @param yycgdQueryVo
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	public List<Yycgdmx> findYycgdmxList(Integer yycgdid,YycgdQueryVo yycgdQueryVo,int firstResult,int maxResults);
	/**
	 * 查询采购单明细总数
	 * @param yycgdid
	 * @param yycgdQueryVo
	 * @return
	 */
	public Long findYycgdmxListCount(Integer yycgdid,YycgdQueryVo yycgdQueryVo);
	/**
	 * 根据采购单id和药品信息id查询一条采购单明细记录
	 * @param yycgdid
	 * @param ypxxid
	 * @return
	 */
	public Yycgdmx findYycgdmxByYycgdidAndYpxxid(Integer yycgdid,String ypxxid);
	/**
	 * 根据采购单id，和采购单明细信息更新
	 * @param yycgdid
	 * @param yycgdmxs 要更新的采购单明细，包括采购量和采购单明细id
	 */
	public void updateYycgdmx(Integer yycgdid,List<Yycgdmx> yycgdmxs);
	/**
	 * 采购单最终提交
	 * @param yycgdid
	 * @param taskId
	 * @param assignee
	 */
	public void saveYycgdSubmitState(Integer yycgdid,String taskId,String assignee);
	//卫生院查询该辖区卫生室提交的采购单
	public List<Yycgd> findCheckYycgdList(String userjdid,YycgdQueryVo yycgdQueryVo,int firstResult,int maxResults);
	//查询采购单明细总数
	public Long findCheckYycgdListCount(String userjdid,YycgdQueryVo yycgdQueryVo);
	/**
	 * 保存采购单的审核状态
	 * @param taskId
	 * @param assignee
	 * @param yycgdid
	 * @param checkResult
	 * @param yycgdCustom
	 */
	public void saveYycgdCheckState(String taskId,String assignee,Integer yycgdid,String checkResult,YycgdCustom yycgdCustom);
	/**
	 * 查询待受理采购单
	 * @param usergysid
	 * @param yycgdQueryVo
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	public List<Yycgd> findDisposeYycgdList(String usergysid,YycgdQueryVo yycgdQueryVo,int firstResult,int maxResults);
	/**
	 * 待受理采购单数量
	 * @param usergysid
	 * @param yycgdQueryVo
	 * @return
	 */
	public Long findDisposeYycgdListCount(String usergysid,YycgdQueryVo yycgdQueryVo);
	/**
	 * 保存采购单详细信息中药品的受理状态
	 * @param yycgdid 采购单id
	 * @param yycgdmxs 采购单详细信息,包括各个药品的受理状态和药品信息id
	 */
	public void saveDisposeState(Integer yycgdid,List<Yycgdmx> yycgdmxs);
	/**
	 * 更新采购单的受理完成状态
	 * @param yycgdid 采购单id
	 * @param taskId 任务id
	 * @param assignee 待办人
	 */
	public void saveFinishDisposeState(Integer yycgdid,String taskId,String assignee);
}
