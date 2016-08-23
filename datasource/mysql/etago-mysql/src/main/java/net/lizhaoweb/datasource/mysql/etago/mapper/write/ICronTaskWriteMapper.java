/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : parent
 * @Package : net.lizhaoweb.datasource.mysql.etago.mapper.write
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Time : 16:37
 */
package net.lizhaoweb.datasource.mysql.etago.mapper.write;

import net.lizhaoweb.datasource.mysql.etago.model.CronTask;
import org.apache.ibatis.annotations.Param;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年07月29日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
public interface ICronTaskWriteMapper {

    /**
     * 插入数据。
     *
     * @param bean 数据对象。
     * @return 返回操作数量。
     */
    Long insert(CronTask bean);

    /**
     * 更新数据。
     *
     * @param bean 数据对象。
     * @return 返回操作数量。
     */
    Long update(CronTask bean);

    /**
     * 删除数据。
     *
     * @param id 记录标识。
     * @return 返回操作数量。
     */
    Long delete(@Param("id") Long id);
}
