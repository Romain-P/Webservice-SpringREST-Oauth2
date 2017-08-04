import { ClktimeAppPage } from './app.po';

describe('clktime-app App', () => {
  let page: ClktimeAppPage;

  beforeEach(() => {
    page = new ClktimeAppPage();
  });

  it('should display welcome message', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('Welcome to app!');
  });
});
