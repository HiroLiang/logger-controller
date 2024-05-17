管理 Logger Marker
===

---

# 1. 初始化設定

## 自製 Marker Config

> 給工廠用的常用 config 設定
```Java=

 public class CustomMarkerConfig implements MarkerConfig {
    /*
     * 時間戳記：
     *     可在需要時更新時間。
     */
    @Override
    public LocalDateTime getTimestamp() {
        return LocalDateTime.of(2024, 5, 1, 0, 0, 0);
    }

    /*
     * 過期天數：
     *     時間戳後過幾天會過期。
     *
     *     例外 -1 ： 皆當未過期 ，0 ；皆當過期。
     */
    @Override
    public Integer getExpirationDays() {
        return -1;
    }
}

```

---

## 自定義 Marker

> 可自己設計從屬關係，再利用 DefaultFilter 篩選屬於此 Marker 的所有 Marker
```Java=

public class CustomsMarker extends StandardMarker {
    public CustomsMarker(String name, LocalDateTime timestamp, Integer expirationDays) {
        super(name, timestamp, expirationDays);
    }
}

```

---

## 自製 Default Marker List

> 自製 Marker 樹，將 List 用 getMarkers() 回傳，Handler 會自己處理
```Java=

@AsDefaultMarkers // 標註為自己要的 Default ，若沒有會使用工具預設 ( 只有 Default marker )
@Component // 須讓自定義的 Default 給 Spring 監管
public class MyDefaultMarkers implements DefaultMarkers {

    /*
     * 複寫介面方法，回傳要使用的預設 Marker List
     */
    @Override
    public List<Marker> getMarkers() {
        return List.of(getCustomMarker());
    }

    private StandardMarker getCustomMarker() {
        CustomMarkerConfig customMarkerConfig = new CustomMarkerConfig();
        return Objects.requireNonNull(MarkerFactory.create(customMarkerConfig, CustomsMarker.class))
                .addChild(MarkerFactory.create(customMarkerConfig, ChildMarker.class));
    }
}

```

---

## 配置 log4j2-spring.xml

```XML=

<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration>

<configuration shutdownHook="disable">
    <appenders>
        <Console name="STDOUT" target="SYSTEM_OUT">
            <PatternLayout charset="UTF-8" pattern="%d [%t] %-5p [%c{1}.%M:%L] - %m%n"/>
            <Filters>
                <DefaultFilter markerName="Customs" onMatch="ACCEPT" onMismatch="DENY"/>
            </Filters>
        </Console>
    </appenders>
    <Loggers>
        <Root level="INFO">
            <AppenderRef ref="STDOUT" />
        </Root>
    </Loggers>
</configuration>

```

---

### 經過以上配置已可使用基礎的功能

```Java=

log.info("Test marker log ... ");
        log.info(MarkerHandler.getMarker("Default").toString());
        log.info(MarkerHandler.getMarker("Default"), "Default marker");
        log.info(MarkerHandler.getMarker("Customs").toString());
        log.info(MarkerHandler.getMarker("Customs"), "Standard marker");
        /*
         * 輸出 :
         * 2024-05-17 19:17:26,934 [http-nio-8080-exec-1] INFO  [DemoController.testLog:29] - Test marker log ...
         * 2024-05-17 19:21:05,729 [http-nio-8080-exec-1] INFO  [DemoController.testLog:30] - Default
         * 2024-05-17 19:21:05,730 [http-nio-8080-exec-1] INFO  [DemoController.testLog:32] - Customs [ Child ]
         * 2024-05-17 19:21:05,730 [http-nio-8080-exec-1] INFO  [DemoController.testLog:33] - Standard marker
         *
         * 當然...因為 Default 不在 Customs 底下 ( 但 Default 一定存在 )
         */

```
