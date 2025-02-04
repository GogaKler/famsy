import type { HeaderTabItem } from './tab-item';

export class HeaderTabList {
  constructor(private tabs: HeaderTabItem[] = []) {}

  addTab(tab: HeaderTabItem): HeaderTabList {
    this.tabs.push(tab);
    return this;
  }

  getTabs(): HeaderTabItem[] {
    return this.tabs;
  }
}