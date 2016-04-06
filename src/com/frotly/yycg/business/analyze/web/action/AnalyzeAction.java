package com.frotly.yycg.business.analyze.web.action;

import java.awt.Color;
import java.awt.Font;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.TextAnchor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.frotly.yycg.base.web.action.BaseAction;
import com.frotly.yycg.business.analyze.pojo.YybusinessCustom;
import com.frotly.yycg.business.analyze.pojo.YybusinssQueryVo;

@Controller
@Scope("prototype")
public class AnalyzeAction extends BaseAction<YybusinssQueryVo> {
	// 按区域统计
	public String sumbyarea() {

		YybusinssQueryVo yybusinssQueryVo = this.getModel();
		try {

			// //数计的结果
			// double[][] data = new double[][] { { 1310, 720.68, 675.3, 560,
			// 680.88, 780 } };
			// // 统计指标
			// String[] rowKeys = { "药品采购金额"};
			// // 统计分组
			// String[] columnKeys = { "崔庙镇", "汜水镇", "高山镇", "城关乡", "刘河镇", "环翠峪"
			// };

			// 创建dataset，集合了生成图形所需要的数据
			// CategoryDataset dataset = DatasetUtilities.createCategoryDataset(
			// rowKeys, columnKeys, data);

			// 调用service取出统计结果
			List<YybusinessCustom> list = serviceFacade.getBusinessService()
					.findSumByArea(yybusinssQueryVo);
			// 采用动态的数据
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			// dataset.addValue(1310, "药品采购金额", "崔庙镇");
			// dataset.addValue(720.68, "药品采购金额", "汜水镇");
			// dataset.addValue(675.3, "药品采购金额", "高山镇");
			// dataset.addValue(560, "药品采购金额", "城关乡");
			// dataset.addValue(680.88, "药品采购金额", "刘河镇");
			// dataset.addValue(780, "药品采购金额", "环翠峪");
			for (YybusinessCustom yybusinessCustom : list) {
				dataset.addValue(yybusinessCustom.getCgje(), "药品采购金额",
						yybusinessCustom.getAreaname());
			}

			// 创建chart用于生成图形
			JFreeChart chart = ChartFactory.createBarChart3D("药品采购金额汇总",// 图形名称
					"",// 分类名称，为横坐标名称
					"采购金额(元)",// 值名称，为纵坐标名称
					dataset,// 数据集合
					PlotOrientation.VERTICAL,// 垂直显示
					false,// 是否显示图例
					false,// 是否使用工具提示
					false);// 是否使用url

			// 在柱上显示数值
			CategoryPlot plot = chart.getCategoryPlot();

			BarRenderer3D renderer = new BarRenderer3D();

			// 设置柱的颜色
			// renderer.setSeriesPaint(0, Color.decode("#ff0000"));

			renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
			renderer.setBaseItemLabelsVisible(true);
			// 默认的数字显示在柱子中，通过如下两句可调整数字的显示
			// 注意：此句很关键，若无此句，数字的显示会被覆盖
			renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(
					ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));
			renderer.setItemLabelAnchorOffset(10D);
			// 设置每个地区所包含的平行柱的之间距离
			// renderer.setItemMargin(0.3);
			plot.setRenderer(renderer);
			// 设置字体，注意：jfreechart默认的字体不支持中文
			// 需要手动设置字体 （支持中文）
			// 配置字体
			Font xfont = new Font("宋体", Font.PLAIN, 12);// X轴
			Font yfont = new Font("宋体", Font.PLAIN, 12);// Y轴
			Font kfont = new Font("宋体", Font.PLAIN, 12);// 底部
			Font titleFont = new Font("宋体", Font.BOLD, 25); // 图片标题
			// 图形的绘制结构对象,对于饼图不适用

			// 图片标题
			chart.setTitle(new TextTitle(chart.getTitle().getText(), titleFont));

			// 底部
			LegendTitle legendTitle = chart.getLegend();
			if (legendTitle != null) {
				legendTitle.setItemFont(kfont);
			}

			// X 轴
			CategoryAxis domainAxis = plot.getDomainAxis();
			domainAxis.setLabelFont(xfont);// 轴标题
			domainAxis.setTickLabelFont(xfont);// 轴数值
			domainAxis.setTickLabelPaint(Color.BLUE); // 字体颜色
			// domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

			// Y 轴
			ValueAxis rangeAxis = plot.getRangeAxis();
			rangeAxis.setLabelFont(yfont);
			rangeAxis.setLabelPaint(Color.BLUE); // 字体颜色
			rangeAxis.setTickLabelFont(yfont);

			// 生成图形，jfreechart将图形生成到tomcat服务器的temp临时目录，jsp浏览临时
			// 目录中的图片通过org.jfree.chart.servlet.DisplayChart 浏览
			// 临时 文件名称
			String jfreechart_filename = ServletUtilities.saveChartAsPNG(chart,
					900, 500, null, this.getRequest().getSession());
			/*
			 * Jsp页面显示图形： <img
			 * src="${baseurl }/jfreechart?filename=${jfreechart_filename }"
			 * border=0 /> /jfreechart 是jfreechart的servlet解析的路径
			 */
			// 需要将图形名称放入值栈，页面来获取
			yybusinssQueryVo.setJfreechart_filename(jfreechart_filename);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 转到jsp
		return "sumbyarea";
	}
}
