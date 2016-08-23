/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : parent
 * @Package : com.easarrive.image.thumbor.exception
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 15:21
 */
package com.easarrive.image.thumbor.exception;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2016年08月01日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
public class ThumborRuntimeException extends RuntimeException {

    public ThumborRuntimeException() {
        super();
    }

    public ThumborRuntimeException(String message) {
        super(message);
    }

    public ThumborRuntimeException(String format, Object... args) {
        super(String.format(format, args));
    }

    public ThumborRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ThumborRuntimeException(Throwable cause) {
        super(cause);
    }
}
