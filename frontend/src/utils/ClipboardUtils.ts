export function copyToClipboard(data: string): void {
  const dummy = document.createElement('textarea');
  document.body.appendChild(dummy);
  dummy.value = data;
  dummy.select();
  document.execCommand('copy');
  document.body.removeChild(dummy);
}
