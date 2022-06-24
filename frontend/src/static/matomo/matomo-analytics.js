try {
  const _paq = (window._paq = window._paq || []);
  /* tracker methods like "setCustomDimension" should be called before "trackPageView" */
  _paq.push(['trackPageView']);
  _paq.push(['enableLinkTracking']);
  (function () {
    const u = 'https://digital.matomo.cloud/';
    _paq.push(['setTrackerUrl', u + 'matomo.php']);
    _paq.push(['setSiteId', '6']);
    const d = document;
    const g = d.createElement('script');
    const s = d.getElementsByTagName('script')[0];
    g.async = true;
    g.src = '//cdn.matomo.cloud/digital.matomo.cloud/matomo.js';
    s.parentNode.insertBefore(g, s);
  })();
} catch (error) {
  console.error(e)
}
