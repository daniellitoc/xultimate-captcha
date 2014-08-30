# xultimate-captcha #

可分布式部署作为验证码生成服务器和验证服务器，支持图片和声音的验证码框架。

* 整套环境基于Spring进行构建，主要采用服务来驱动贫血模型完成无状态服务。
* 声音部分推荐采用配置文件方式、图片推荐采用自定义FactoryBean完成，默认提供了4个实现，声音和图片的各个层次都是无状态的服务类，因此可通过Spring灵活配置。
* 通过过滤器做起始点，内部通过组合方式配置一个生成器和多个拦截器。拦截器可实现前置和后置处理。
* 验证码作为一个操作实体，通过拦截器机制进行灵活扩展，包括记录日志、验证参数、向客户端响应图片或声音等。
* 实体域和和相关服务域通通采用泛型类。其中Q: 问题所属类型；A: 问题答案所属类型；UA: 用户输入的答案所属类型；R: 与Q配合，表示对应问题的结果类型(图象、音频)。


### 实体 ###

* Captcha\<Q, A\>: 验证码实体域，包括问题实体域和答案实体域。DefaultCaptcha\<Q, A\>为其默认实现。
* Question\<Q\>: 问题实体域。DefaultQuestion\<Q\>为其默认实现。
* Answer\<A\>: 答案实体域。DefaultAnswer\<A\>为其默认实现。
* Validatable\<A, UA\>: 定义可验证接口，提供了验证方法，类似于Comparable。
* Validator\<A, UA\>: 验证器，用于定义用户输入的和自己生成的答案之间的验证规则，类似于Comparator。StringStringValidator为其默认实现，用于验证用户输入String类型，答案为String类型的验证器。
* ValidatableAnswer\<A, UA\>: 继承了DefaultAnswer\<A\>，并且实现了Validatable\<A, UA\>接口，提供验证方法，包含一个Validator\<A, UA\>实例，用于定于验证规则。
* AnswerDto\<UA\>: 客户端进行远程验证时需要的传输对象。


### 实体域服务器 ###

* CaptchaGenerator\<Q, A\>: 验证码实体域生成器。生成方式提供从指定字符、数字、单词、组合单词等。
* QuestionHandler\<Q, R\>: 问题实体域处理器，用于根据问题实体域转换为图片或声音。
* AnswerHandler\<UA\>: 答案实体域处理器，传输AnswerDto\<UA\>用于判断是否验证成功。


### 声音 ###

* 声音实体Sample和声音合成类Mixer是采用SimpleCaptcha的实现。
* 声音部分主要通过配置来指定字符声音文件、字符匹配表达式、替换KEY、整体渲染声音文件。合成部分主要通过随机生成器和渲染器完成。
* 提供VoiceGenerator，用于根据字符产生声音片段，默认实现DefaultVoiceGenerator。
* 提供DefaultNoiseRenderer，用于将声音片段(多个声音实体)和背景渲染声音实体组合成一个声音实体。
* 提供AudioQuestionHandler，QuestionHandler\<String, Sample\>形式的默认实现，内部封装多个生成器和多个渲染器，实现随机功能。
* 声音部分的各个组建都是采用Spring配置的方式来定义，各个组建提供无状态服务组合完成。


### 图片 ###

* 图片处理部分主要依据JCaptcha，在保留功能的情况下，进行重构。保留Glyphs。
* 构建后的结构为BackgroundGenerator、BufferedImageRenderer、GlyphsDecorator、GlyphsVisitors。
* 提供AbstractQuestionHandler\<Q\>，实现了QuestionHandler\<Q, BufferedImage\>接口，定义了宽度、高度、背景图片生成器、北京图片渲染器、最终图片渲染器。定义基本操作，由子类具体完成特效处理并返回新的BufferedImage。
* 提供GlyphsQuestionHandler，AbstractQuestionHandler\<String\>子类，为JCaptcha的功能最终合成类。
* 提供DeformedComposedQuestionHandler\<Q\>，实现了QuestionHandler\<Q, BufferedImage\>，提供高级处理，内部封装QuestionHandler\<Q, BufferedImage\>，主要实现添加边框和重新渲染功能。
* 主要思路是去除其复杂化(各种自定义类型)、并实现各个部分都可以通过Spring配置的方式定义，提供无状态服务。
* 如果觉得通过Spring配置过于麻烦，可通过自定义FactoryBean完成，默认提供了GmailQuestionHandlerFactoryBean、Hotmail2008QuestionHandlerFactoryBean、HotmailQuestionHandlerFactoryBean为原有功能的重新实现，LikeTBQuestionHandlerFactory则是一个类taobao的实现，添加模糊处理和若干干扰。


### Web ###

* 提供CaptchaInterceptor\<Q, A\>，包括前置处理和后置处理。前置方法包括request和response，后置方法包括request、response、Captcha\<Q, A\>。
* 提供AbstractCaptchaInterceptor\<Q, A\>，多了一个获取优先级方法，优先级越高，前置和后置方法分别越先执行。
* 提供CaptchaFilter\<Q, A\>，通用实现，封装一个CaptchaGenerator\<Q, A\>和多个AbstractCaptchaInterceptor\<Q, A\>，拦截器的前置和后置分别在Captcha\<Q, A\>生成前后被调用。
* 若有新的需求，可通过扩展AbstractCaptchaInterceptor\<Q, A\>实现，而不必修改CaptchaFilter\<Q, A\>。
* 默认提供拦截器BrowserCacheCaptchaInterceptor\<Q, A\>，实现客户端缓存功能。
* 默认提供拦截器AbstractErrorBufferedImageCaptchaInterceptor\<Q, A\>，提供输出错误图片功能。
* 默认提供拦截器LoggerRecordStringCaptchaInterceptor\<Q, A\>，记录日志信息，包括identity(所属站点)、sessionId(用户标识，非会话ID)、remoteAddr(客户端IP)、currentTime。
* 默认提供拦截器BufferedImageCaptchaInterceptor\<Q, A\>，根据sessionid是否存在，发送错误图片或通过QuestionHandler\<Q, BufferedImage\>生成图片发送给客户端。
* 默认提供拦截器AudioCaptchaInterceptor\<Q, A\>，根据sessionid是否存在，发送错误图片或通过QuestionHandler\<Q, Sample\>生成音频发送给客户端。
* 默认提供拦截器XMemcachedClientCaptchaInterceptor\<Q, A\>，通过验证码ID表达式、替换表达式、格式化器、sessionId生成captchaId作为memcached的key，value使用Answer\<A\>(答案实体域)进行存储。
* 过滤器和拦截器都是通过Spring配置的方式来定义和提供无状态服务。


### 客户端验证 ###

* 提供ValidatableAnswerHandler\<A, UA\>，通过验证码ID表达式、替换表达式、格式化器、sessionId生成captchaId作为memcached的key进行查找，返回ValidatableAnswer\<A, UA\>并调用validateTo(UA userAnswer)方法完成验证。并记录验证行为日志。
