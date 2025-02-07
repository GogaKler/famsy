type THeaderTabItem = {
  routeName: string;
  name: string;
  icon: string;
  counter?: number | null;
};

export class HeaderTabItem {
  public routeName: string;
  public name: string;
  public icon: string;
  public counter?: number | null;

  constructor(tab: THeaderTabItem) {
    this.routeName = tab.routeName;
    this.name = tab.name;
    this.icon = tab.icon;
    this.counter = tab.counter;
  }
}
