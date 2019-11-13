package markup;

import java.util.List;

public abstract class ContainerOfHighlight extends AbstractContainer implements PartOfHighlight {
    public ContainerOfHighlight(List<PartOfHighlight> list) {
        super(list);
    }
}
