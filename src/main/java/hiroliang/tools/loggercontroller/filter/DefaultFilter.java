package hiroliang.tools.loggercontroller.filter;

import hiroliang.tools.loggercontroller.marker.StandardMarker;
import hiroliang.tools.loggercontroller.util.MarkerHandler;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Node;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.filter.AbstractFilter;

/*
 * Default Filter :
 *     簡單過濾未過期，且包含在 Marker reference 中的 Marker
 */
@Plugin(name = "DefaultFilter", category = Node.CATEGORY)
public class DefaultFilter extends AbstractFilter {

    private final String markerName; // 先取得 xml 中設定的名稱 ( 防止 Filter 實例化時沒有 Handler 用 )

    private DefaultFilter(String markerName, Result onMatch, Result onMismatch) {
        super(onMatch, onMismatch);
        this.markerName = markerName;
    }

    @Override
    public Result filter(LogEvent event) {
        // 去 Handler 中找到對應的 Standard Marker
        StandardMarker markerForFilter = (StandardMarker) MarkerHandler.getMarker(markerName);

        // 未使用 Marker ，放任通過
        if (event.getMarker() == null)
            return Result.NEUTRAL;

        // Log4j 會把 Slf4j 的 marker 轉型成自己的，所以用 getName() 重新找到使用的 Marker
        StandardMarker standardMarker = (StandardMarker) MarkerHandler.getMarker(event.getMarker().getName());

        // 時間戳超時，不予通過
        if (standardMarker.isOverdue())
            return onMismatch;

        // 若未匹配到
        if (markerForFilter.contains(standardMarker))
            return onMatch;
        return onMismatch;
    }

    @PluginFactory
    public static DefaultFilter createFilter(@PluginAttribute("markerName") String markerName,
                                             @PluginAttribute("onMatch") final Result onMatch,
                                             @PluginAttribute("onMismatch") final Result onMismatch) {
        System.out.println("DefaultFilter createFilter ...");
        return new DefaultFilter(markerName, onMatch, onMismatch);
    }
}
