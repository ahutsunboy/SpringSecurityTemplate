

# SpringSecurity 操作步骤说明

### 1.创建SpringBoot工程，添加依赖包(redis,jjwt,fastjson,security)

### 2.创建实体类实现序列化接口，数据源配置

### 3.创建安全用户类实现UserDetails接口，重写方法并将实体类用户封装进去，注意密码获取后返回，然后将用户信息中密码置空

#### （1）在此处就可以直接将该类成员变量设置好：分别是：用户实体类、字符串集合权限、标准格式权限  （2）构建含有字符串集合权限和实体类用户的构造方法，便于后期数据库查到权限集合后直接存入。

- 注意：在此设置好权限等操作，第十一步中则可以减少该步骤

### 4.(1)创建service层得实现类，实现UserDetailService接口，先通过用户名查询用户信息(另写service和mapper查库获得)(2)用户名存在则再查库得用户权限，将用户权限装载进入安全用户(? extends UserDetails)中，建议使用数据流，效率高。获取权限步骤可以后来再写

### 5.完成各项配置和工具类的引入， 

(1)导入写好的配置类工具,包括FastJsonRedisSerializer、RedisCache、RedisConfig。       
(2)导入工具类JwtUtil，WebUtils       (3).设置响应类ResponseResult(code,msg,data)

#### WebUtils  工具类是封装了response返回给也页面的方法。方便多次调用减少代码冗余

### 6.WebSecurityConfig的配置

#### 不再继承与WebSecurityConfigurerAdapter，而只是一个普通配置类，（1）在其中设置编码类型BCryptPasswordEncoder注入容器。

#### （2）依照模板编写SecurityFilterChain设置路径权限和放行、用户权限、跨域问题、关闭session、登录、退出等操作成功失败的自定义配置。

#### 非此时的步骤：后面登录校验需要用到ProviderManager中的认证方法，需要在这配置AuthenticationManager注入容器，但是配置这个需要先引入AuthenticationConfiguration自动配置类

### 7.开始登录操作的controller的编写，引入service层login方法

### 9.编写service层

#### （1）ProviderManager中的认证方法，但是这个方法参数是Authentication对象，需要先获取其对象，但是其为接口，所以选择其实现类中的UsernamePasswordAuthenticationToken类，new出其对象，参数为用户名和密码，然后认证方法判断这个对象，获取Authentication对象，如果为空，则抛出用户名错误，否则正确，从这个对象中获取安全用户类

#### （2）在获取其中的实体用户类后获取其id，将这个用户id去创建jwt，在将用户信息安全用户类存入redis中，返回成功的响应类,响应类中包含token（即值为jwt）

### 9.添加认证过滤器

#### 继承OncePerRequestFilter，主要实现以下功能：1、获取token   2、解析token 3、获取userId,获取redis中的用户信息  4、封装到Authentication   5、存入SecurityContextHandler  6、放行  7、去webS ecurity中去添加过滤器到用户名密码验证过滤器前面

#### 切不可忘记上述第七步

#### 补充：token从请求头获取，解析了token就能从中获取userId，使用userId带到redis中去作为键的一部分，获取存在redis中的用户信息（安全用户类），在创建Authentication的实现类UsernamePasswordAuthenticationToken(含三参数的那个)，将用户信息(安全用户类存入其中作为第一个参数)，将这个Authentication对象存入SecurityContextHolder。

### 10.用户退出，获取SecurityContextHodler中的Authentication对象，再从中获取到安全用户类，随之获取实体类用户，再用其userId作为redis键的一部分去删除redis值，则退出成功，下次访问时候，无法从redis中获取用户信息则直接在过滤器中被刷下

### 11.添加权限控制

#### （1）webSecurityConfig配置类上添加EnableGlobalMethodSecurity(prePostEnabled = true)注解

#### （2）在安全用户类中添加权限的成员变量，以及字符串集合形式的权限信息，并在第一个重写方法中在其中用stream流转化为权限信息，其次设置含有用户实体类对象和字符串集合的权限信息的构造方法，为后面传入数据简化方法

- 注意：一定要在”权限“成员变量上加上    
  @JSONField(serialize = false)注解

#### （3）在service层UserDetailsService的实现类中设置获取登录用户的返回值中加入从数据库获取查询的权限信息存入安全用户类中返回。

- 这里需要先调用mapper查询出用户存在数据库中的权限信息，这里一般都要联表查询

#### （4）在jwt认证过滤器JwtAuthenticationTokenFilter中的封装Authentication对象中三个参数中添加进去用户权限信息

- 封装Authentication对象得对象指的是UsernamePasswordAuthenticationToken的对象

### 12.异常处理

#### （1）创建异常处理类的包，分别创建AuthenticationEntryPoint、AccessDeniedHandler 两个类的实现类，然后重写方法（创建响应类对象，二者分别传入用户未认证code和用户禁止访问code，和对应的msg），然后调用JSON中的方法使得响应类对象转化为json字符串，然后借用WebUtils工具类返回给前端。

- 两个异常处理器，有着很相似的内容

#### （2）分别设置好以上两个异常处理器之后，将其添加到安全配置类WebSecurityConfig的http . exceptionHandling中

### 至此用户登录接口实现，未写过滤器，处理登录接口，其他任何路径都访问不了，及时这个路径权限是登陆即可的也不行

