import click
import matplotlib.pyplot as plt


@click.command()
@click.option('--f')
def build_graphics(f):
    """Simple program that build graphics with statistics."""
    file = open(f)

    all_statistics = {}
    for line in file:
        test = line.split('(')[1].split(')')[0]
        new_line = line.split(' - ')
        all_statistics[test] = int(new_line[1])

    elements = []
    numbers = []
    for key in all_statistics:
        elements.append(all_statistics[key])

    for key in all_statistics:
        numbers.append(key)

    fig, ax = plt.subplots(figsize=(14.5, len(elements) / 2))
    ax.set_yticks(range(len(numbers)))
    ax.set_yticklabels(numbers, rotation=45)
    for i in range(len(all_statistics)):
        ax.hlines(i, 0, elements[i], colors='b')
    fig.savefig('PsiStatistic' + f + '.png')
    plt.close(fig)


if __name__ == '__main__':
    build_graphics()
